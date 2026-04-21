import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { PersonalityService, QuestionRound, RoundAnswer } from '../../services/personality';

interface RankableOption {
  personality: string;
  text: string;
  rank: number | null;
}

@Component({
  selector: 'app-test',
  imports: [CommonModule],
  templateUrl: './test.html',
  styleUrl: './test.scss',
})
export class TestComponent implements OnInit {
  rounds: QuestionRound[] = [];
  currentRound = 0;
  answers: RoundAnswer[] = [];
  currentOptions: RankableOption[] = [];
  loading = true;
  submitting = false;
  errorMessage = '';

  constructor(
    private personalityService: PersonalityService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.loading = true;
    this.errorMessage = '';
    this.personalityService.getQuestions().subscribe({
      next: (rounds) => {
        this.rounds = rounds;
        this.loading = false;
        this.setupRound();
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading questions:', err);
        this.loading = false;
        this.errorMessage = 'No se pudo conectar al servidor.';
        this.cdr.detectChanges();
      }
    });
  }

  setupRound() {
    if (this.currentRound < this.rounds.length) {
      this.currentOptions = this.rounds[this.currentRound].options.map(opt => ({
        personality: opt.personality,
        text: opt.text,
        rank: null
      }));
    }
  }

  assignRank(optionIndex: number, rank: number) {
    this.currentOptions.forEach(opt => {
      if (opt.rank === rank) {
        opt.rank = null;
      }
    });
    this.currentOptions[optionIndex].rank = rank;
  }

  isRankUsed(rank: number): boolean {
    return this.currentOptions.some(opt => opt.rank === rank);
  }

  isRoundComplete(): boolean {
    return this.currentOptions.every(opt => opt.rank !== null);
  }

  get progressPercent(): number {
    return ((this.currentRound) / this.rounds.length) * 100;
  }

  nextRound() {
    if (!this.isRoundComplete()) return;

    const roundAnswer: RoundAnswer = {};
    this.currentOptions.forEach(opt => {
      roundAnswer[opt.personality] = opt.rank!;
    });
    this.answers.push(roundAnswer);

    this.currentRound++;

    if (this.currentRound >= this.rounds.length) {
      this.submitTest();
    } else {
      this.setupRound();
    }
  }

  submitTest() {
    this.submitting = true;
    this.personalityService.evaluate({ rounds: this.answers }).subscribe({
      next: (result) => {
        this.router.navigate(['/results'], { state: { result } });
      },
      error: (err) => {
        console.error('Error evaluating:', err);
        this.submitting = false;
        this.errorMessage = 'Error al enviar las respuestas.';
        this.cdr.detectChanges();
      }
    });
  }

  getPersonalityColor(personality: string): string {
    const colors: { [key: string]: string } = {
      'LEON': 'var(--leon-color)',
      'NUTRIA': 'var(--nutria-color)',
      'GOLDEN': 'var(--golden-color)',
      'CASTOR': 'var(--castor-color)'
    };
    return colors[personality] || '#6c5ce7';
  }
}
