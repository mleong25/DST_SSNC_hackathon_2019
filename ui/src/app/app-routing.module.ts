import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CcbComponent} from './ccb/ccb.component';
import { AboutUsComponent} from './about-us/about-us.component';
import { HomePageComponent } from './home-page/home-page.component';
import { CommandsComponent } from './commands/commands.component'
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  { path: 'troubleshoot', component: CcbComponent },
  { path: 'about-the-team', component: AboutUsComponent },
  { path: 'home-page', component: HomePageComponent },
  { path: '', component: HomePageComponent},
  { path: 'config', component: CommandsComponent}
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
