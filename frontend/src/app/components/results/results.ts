import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { TestResult, PersonalityRank } from '../../services/personality';

@Component({
  selector: 'app-results',
  imports: [CommonModule],
  templateUrl: './results.html',
  styleUrl: './results.scss',
})
export class ResultsComponent implements OnInit {
  result: TestResult | null = null;
  maxScore = 40;

  private emojiMap: { [key: string]: string } = {
    'LEON': '\u{1F981}',
    'NUTRIA': '\u{1F9A6}',
    'GOLDEN': '\u{1F436}',
    'CASTOR': '\u{1F9AB}'
  };

  private colorMap: { [key: string]: string } = {
    'LEON': 'var(--leon-color)',
    'NUTRIA': 'var(--nutria-color)',
    'GOLDEN': 'var(--golden-color)',
    'CASTOR': 'var(--castor-color)'
  };

  constructor(private router: Router) {}

  ngOnInit() {
    const nav = this.router.getCurrentNavigation();
    if (nav?.extras?.state?.['result']) {
      this.result = nav.extras.state['result'];
    } else if (history.state?.result) {
      this.result = history.state.result;
    } else {
      this.router.navigate(['/']);
    }
  }

  getEmoji(key: string): string {
    return this.emojiMap[key] || '';
  }

  getColor(key: string): string {
    return this.colorMap[key] || 'var(--accent)';
  }

  getBarWidth(score: number): number {
    return (score / this.maxScore) * 100;
  }

  retakeTest() {
    this.router.navigate(['/test']);
  }

  goHome() {
    this.router.navigate(['/']);
  }
}
