export class BenefitService {
    constructor(apiBaseUrl = "http://localhost:8080/api/benefits") {
        this.apiBaseUrl = apiBaseUrl;
    }
    async createBenefit(request) {
        // create endpoint persists input and returns calculated payment breakdown.
        const response = await fetch(this.apiBaseUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(request),
        });
        return this.parseResponse(response);
    }
    async getBenefitById(id) {
        const response = await fetch(`${this.apiBaseUrl}/${id}`, {
            method: "GET",
        });
        return this.parseResponse(response);
    }
    async parseResponse(response) {
        if (!response.ok) {
            let message = `Request failed with status ${response.status}`;
            try {
                // backend may send structured error payloads; prefer message when available.
                const errorPayload = await response.json();
                if (errorPayload?.message && typeof errorPayload.message === "string") {
                    message = errorPayload.message;
                }
            }
            catch {
                // ignore json parse failures and use default message.
            }
            throw new Error(message);
        }
        return response.json();
    }
}
