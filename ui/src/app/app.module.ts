import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CCLogoComponent } from './cc-logo.component';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { CcbComponent } from './ccb/ccb.component';
import { MenuComponent } from './menu/menu.component';
import { CommandsComponent } from './commands/commands.component';
import { ResponseComponent } from './commands/response/response.component';
import { RequestComponent } from './commands/request/request.component';
import { CommandInfoComponent } from './commands/command-info/command-info.component';
import { ArgumentComponent } from './commands/argument/argument.component';
import { MatIconModule, MatTabsModule, MatSelectModule, MatCheckboxModule,
  MatToolbarModule,MatFormFieldModule,MatInputModule,
  MatButtonModule,
  MatCardModule
} from '@angular/material';
import { AboutUsComponent } from './about-us/about-us.component';
import { HomePageComponent } from './home-page/home-page.component';
@NgModule({
  declarations: [
    AppComponent,
    CcbComponent,
    MenuComponent,
    AboutUsComponent,
    HomePageComponent,
    CCLogoComponent,
    CommandsComponent,
    ResponseComponent,
    RequestComponent,
    CommandInfoComponent,
    ArgumentComponent
  ],
  imports: [
    BrowserModule,BrowserAnimationsModule,
    AppRoutingModule,ReactiveFormsModule, FormsModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatTabsModule,MatInputModule,
    MatSelectModule,MatFormFieldModule,
    MatCheckboxModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
