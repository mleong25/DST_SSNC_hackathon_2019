import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { CcbComponent } from './ccb/ccb.component';
import { MenuComponent } from './menu/menu.component';
import { MatIconModule,
  MatToolbarModule,
  MatButtonModule
} from '@angular/material';
import { AboutUsComponent } from './about-us/about-us.component';
@NgModule({
  declarations: [
    AppComponent,
    CcbComponent,
    MenuComponent,
    AboutUsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
