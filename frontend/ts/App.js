import { BenefitService } from "./benefitService.js";
const STORAGE_KEY = "parental-benefit:lastBenefitId";
const benefitService = new BenefitService();
const form = getRequiredElement("#benefit-form");
const salaryInput = getRequiredElement("#gross-salary");
const birthDateInput = getRequiredElement("#birth-date");
const loadSavedButton = getRequiredElement("#load-btn");
const statusElement = getRequiredElement("#status");
const savedIdElement = getRequiredElement("#saved-id");
const statsElement = getRequiredElement("#stats");
const paymentsBody = getRequiredElement("#payments-body");
const benefitIdInput = getRequiredElement("#benefit-id");
const loadByIdButton = getRequiredElement("#load-by-id-btn");
init();
function init() {
    renderEmptyState();
    form.addEventListener("submit", onFormSubmit);
    loadSavedButton.addEventListener("click", onLoadSavedClick);
    loadByIdButton.addEventListener("click", onLoadByIdClick);
    const savedId = getSavedId();
    if (savedId) {
        benefitIdInput.value = String(savedId);
        setStatus(`Saved benefit ID detected (${savedId}). You can load it anytime.`);
    }
}
async function onFormSubmit(event) {
    event.preventDefault();
    const grossSalary = Number(salaryInput.value);
    const birthDate = birthDateInput.value;
    if (!Number.isFinite(grossSalary) || grossSalary <= 0) {
        setStatus("Please enter a valid gross salary greater than 0.", true);
        return;
    }
    if (!birthDate) {
        setStatus("Please select a valid birth date.", true);
        return;
    }
    setStatus("Calculating benefit...");
    try {
        // backend create call doubles as persistence, then we store id locally for resume.
        const result = await benefitService.createBenefit({
            grossSalary,
            birthDate,
        });
        saveId(result.id);
        benefitIdInput.value = String(result.id);
        renderResult(result);
        setStatus("Calculation saved successfully.");
    }
    catch (error) {
        const message = error instanceof Error ? error.message : "Calculation failed.";
        setStatus(message, true);
    }
}
async function onLoadSavedClick() {
    const savedId = getSavedId();
    if (!savedId) {
        setStatus("No saved progress found in this browser.", true);
        return;
    }
    await loadAndRenderById(savedId);
}
async function onLoadByIdClick() {
    const id = Number(benefitIdInput.value);
    if (!Number.isInteger(id) || id <= 0) {
        setStatus("Enter a valid benefit ID.", true);
        return;
    }
    await loadAndRenderById(id);
}
async function loadAndRenderById(id) {
    setStatus(`Loading benefit #${id}...`);
    try {
        const result = await benefitService.getBenefitById(id);
        saveId(result.id);
        salaryInput.value = String(result.grossSalary);
        birthDateInput.value = result.birthDate;
        benefitIdInput.value = String(result.id);
        renderResult(result);
        setStatus(`Loaded benefit #${id}.`);
    }
    catch (error) {
        const message = error instanceof Error ? error.message : "Failed to load saved benefit.";
        setStatus(message, true);
    }
}
function renderResult(result) {
    savedIdElement.textContent = `Saved Benefit ID: ${result.id}`;
    statsElement.innerHTML = [
        makeStatChip(`Gross salary: EUR ${formatMoney(result.grossSalary)}`),
        makeStatChip(`Capped salary: EUR ${formatMoney(result.cappedSalary)}`),
        makeStatChip(`Daily rate: EUR ${formatMoney(result.dailyRate)}`),
    ].join("");
    // month labels originate from backend, so escape to avoid accidental html injection.
    paymentsBody.innerHTML = result.monthlyPayments
        .map((payment) => `
      <tr>
        <td>${escapeHtml(payment.month)}</td>
        <td>${payment.days}</td>
        <td>EUR ${formatMoney(payment.payment)}</td>
      </tr>
    `)
        .join("");
}
function renderEmptyState() {
    savedIdElement.textContent = "";
    statsElement.innerHTML = "";
    paymentsBody.innerHTML = `
    <tr>
      <td colspan="3" class="empty">Submit input to see the 12-month payment breakdown.</td>
    </tr>
  `;
}
function setStatus(message, isError = false) {
    statusElement.textContent = message;
    statusElement.style.color = isError ? "#9b1d1d" : "";
}
function formatMoney(value) {
    return Number(value).toFixed(2);
}
function makeStatChip(text) {
    return `<span class="stat-chip">${escapeHtml(text)}</span>`;
}
function escapeHtml(value) {
    return value
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#39;");
}
function saveId(id) {
    localStorage.setItem(STORAGE_KEY, String(id));
}
function getSavedId() {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) {
        return null;
    }
    const parsed = Number(raw);
    if (!Number.isInteger(parsed) || parsed <= 0) {
        return null;
    }
    return parsed;
}
function getRequiredElement(selector) {
    // fail fast on missing markup to avoid silent null errors later.
    const element = document.querySelector(selector);
    if (!element) {
        throw new Error(`App initialization failed: missing element ${selector}`);
    }
    return element;
}
