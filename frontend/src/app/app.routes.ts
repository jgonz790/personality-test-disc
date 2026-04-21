import { Routes } from '@angular/router';
import { WelcomeComponent } from './components/welcome/welcome';
import { TestComponent } from './components/test/test';
import { ResultsComponent } from './components/results/results';

export const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'test', component: TestComponent },
  { path: 'results', component: ResultsComponent },
  { path: '**', redirectTo: '' }
];
