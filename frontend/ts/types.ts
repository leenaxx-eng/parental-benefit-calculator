export interface BenefitRequest {
  grossSalary: number;
  birthDate: string;
}

export interface MonthlyPayment {
  month: string;
  days: number;
  payment: number;
}

export interface BenefitResponse {
  id: number;
  grossSalary: number;
  birthDate: string;
  cappedSalary: number;
  dailyRate: number;
  monthlyPayments: MonthlyPayment[];
}
