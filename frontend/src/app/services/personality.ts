import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface QuestionOption {
  personality: string;
  text: string;
}

export interface QuestionRound {
  roundNumber: number;
  options: QuestionOption[];
}

export interface PersonalityRank {
  position: number;
  key: string;
  name: string;
  emoji: string;
  score: number;
}

export interface PersonalityInfo {
  key: string;
  name: string;
  emoji: string;
  keyword: string;
  description: string;
  negatives: string[];
  positives: string[];
  needs: string[];
}

export interface TestResult {
  scores: { [key: string]: number };
  ranking: PersonalityRank[];
  dominantPersonality: PersonalityInfo;
}

export interface RoundAnswer {
  [personality: string]: number;
}

export interface TestAnswers {
  rounds: RoundAnswer[];
}

@Injectable({
  providedIn: 'root',
})
export class PersonalityService {
  private apiUrl = '/api';

  constructor(private http: HttpClient) {}

  getQuestions(): Observable<QuestionRound[]> {
    return this.http.get<QuestionRound[]>(`${this.apiUrl}/questions`);
  }

  evaluate(answers: TestAnswers): Observable<TestResult> {
    return this.http.post<TestResult>(`${this.apiUrl}/evaluate`, answers);
  }

  getPersonalities(): Observable<PersonalityInfo[]> {
    return this.http.get<PersonalityInfo[]>(`${this.apiUrl}/personalities`);
  }
}
