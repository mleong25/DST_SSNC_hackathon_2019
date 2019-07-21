import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CcbComponent} from './ccb/ccb.component';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  { path: 'ccb', component: CcbComponent },
  { path: '', component: CcbComponent}
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
