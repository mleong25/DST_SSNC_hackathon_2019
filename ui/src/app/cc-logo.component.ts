import { Component, Input, ViewChild, OnInit } from '@angular/core';

@Component({
  selector: 'cc-logo',
  template: `<canvas #canv class="cc-logo" [style.width]="diameter + 'px'" [style.height]="diameter + 'px'"></canvas>`,
  styles: [``]
})
export class CCLogoComponent implements OnInit  {
  @Input() diameter = 64;
  @Input() primary = "#0a85c7";
  @Input() secondary = "#fff";

  @Input() animated = false;
  @Input() duration = 1000;

  @ViewChild('canv', {static: true}) canvasReference;
  canvas: HTMLCanvasElement;
  context: CanvasRenderingContext2D;

  startTime: number;

  ngOnInit() {
    this.canvas = this.canvasReference.nativeElement;
    this.canvas.width = this.diameter;
    this.canvas.height = this.diameter;

    this.context = this.canvas.getContext('2d');
    this.context.translate(this.diameter/2, this.diameter/2);

    if (this.animated) {
      requestAnimationFrame(this.animate);
    } else {
      this.draw(-45, 90);
    }
  }

  animate = () => {
    if (this.startTime == undefined) {
      this.startTime = +new Date();
    }

    const progress = +new Date() - this.startTime;
    const percent = progress / this.duration;
    this.clearAll();

    if (percent < 1) {
      const sign = Math.sign(percent - .5);
      const multiplier = ((Math.cos(percent*Math.PI) * -sign) ** .85) * sign * .5 + .5;

      this.draw(-225+540*multiplier, 360-270*multiplier);

      requestAnimationFrame(this.animate);
    } else if (percent >= 1) {
      this.draw(-45, 90);
    }
  }

  draw = (cutStart: number, cutDegrees: number) => {
    const outerCircle = new Path2D();
    outerCircle.arc(0, 0, this.diameter/2, 0, 8);
    this.context.fillStyle = this.primary;
    this.context.fill(outerCircle);

    const middleCircle = new Path2D();
    middleCircle.arc(0, 0, this.diameter*11/32, 0, 8);
    this.context.fillStyle = this.secondary;
    this.context.fill(middleCircle);

    this.clearAngle(cutStart, cutDegrees);

    const innerCircle = new Path2D();
    innerCircle.arc(0, 0, this.diameter*3/16, 0, 8);
    this.context.fillStyle = this.primary;
    this.context.fill(innerCircle);
  }

  clearAll() {
    this.context.globalCompositeOperation = "destination-out";
    this.context.fillRect(-this.diameter, -this.diameter, this.diameter, this.diameter);
    this.context.globalCompositeOperation = "source-over";
  }

  clearAngle(startAng: number, degrees: number) {
    this.context.globalCompositeOperation = "destination-out";

    degrees = Math.min(Math.max(0, degrees),360);

    while (startAng < 0) {
      startAng += 360;
    }
    while (startAng >= 360) {
      startAng -= 360;
    }

    const path = new Path2D();
    path.moveTo(0,0);

    let finished = false;

    do {
      finished = !degrees;
      path.lineTo(
        this.diameter * Math.cos((startAng + degrees) * Math.PI / 180),
        this.diameter * Math.sin((startAng + degrees) * Math.PI / 180)
      );
      degrees = Math.max(0, degrees - 15);
    }
    while (!finished)

    this.context.fill(path);
    this.context.globalCompositeOperation = "source-over";
  }
}
