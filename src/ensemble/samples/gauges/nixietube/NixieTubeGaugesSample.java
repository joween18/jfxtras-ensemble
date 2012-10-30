/*
 * Copyright (c) 2012, JFXtras
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package ensemble.samples.gauges.nixietube;

import ensemble.Sample;
import java.util.Calendar;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jfxtras.labs.scene.control.gauge.NixieTube;
import jfxtras.labs.scene.control.gauge.NixieTubeBuilder;

/**
 * Nixie Tube..
 *
 * @see jxftras.labs.scene.control.gauge.NixieTube
 */
public class NixieTubeGaugesSample extends Sample {
    private static final Random  RND          = new Random();
    private static final long    TIME_PERIOD  = 1000000000l;
    private static final long    COLOR_PERIOD = 5000000000l;
    private static final Color[] GLOW_COLORS = {
        Color.RED,
        Color.LIME,
        Color.YELLOW,
        Color.ORANGE,
        Color.BLUE
    };
    private long                 lastTimeCall = 0;
    private long                 lastColorCall = 0;
    private NixieTube            tube1;
    private NixieTube            tube2;
    private NixieTube            tube3;
    private NixieTube            tube4;
    private NixieTube            tube5;
    private NixieTube            tube6;
    private final AnimationTimer TIMER        = new AnimationTimer() {
        @Override
        public void handle(long l) {
            long currentNanoTime = System.nanoTime();
            if (currentNanoTime > lastTimeCall + TIME_PERIOD) {
                setClock();
                lastTimeCall = System.nanoTime();
            }
            if (currentNanoTime > lastColorCall + COLOR_PERIOD) {
                int index = RND.nextInt(4);
                tube1.setGlowColor(GLOW_COLORS[index]);
                tube2.setGlowColor(GLOW_COLORS[index]);
                tube3.setGlowColor(GLOW_COLORS[index]);
                tube4.setGlowColor(GLOW_COLORS[index]);
                tube5.setGlowColor(GLOW_COLORS[index]);
                tube6.setGlowColor(GLOW_COLORS[index]);
                lastColorCall = System.nanoTime();
            }
        }
    };

    public NixieTubeGaugesSample() {
        super(600, 600);

        // Create some controls
        tube1 = NixieTubeBuilder.create().prefWidth(70).prefHeight(118).build();
        tube2 = NixieTubeBuilder.create().prefWidth(70).prefHeight(118).build();
        tube3 = NixieTubeBuilder.create().prefWidth(70).prefHeight(118).build();
        tube4 = NixieTubeBuilder.create().prefWidth(70).prefHeight(118).build();
        tube5 = NixieTubeBuilder.create().prefWidth(70).prefHeight(118).build();
        tube6 = NixieTubeBuilder.create().prefWidth(70).prefHeight(118).build();

        // Layout
        final HBox clock = new HBox();
        clock.setSpacing(5);
        clock.getChildren().addAll(tube1, tube2, tube3, tube4, tube5, tube6);

        StackPane pane = new StackPane();
        Rectangle background = new Rectangle(445, 120);
        background.setFill(Color.BLACK);
        pane.getChildren().addAll(background, clock);

        getChildren().add(pane);
        setClock();
    }

     private void setClock() {
        Calendar cal = Calendar.getInstance();
        int hh = cal.get(Calendar.HOUR_OF_DAY);
        int mm = cal.get(Calendar.MINUTE);
        int ss = cal.get(Calendar.SECOND);

        // Hours
        if (hh < 10) {
            tube1.setNumber(0);
            tube2.setNumber(hh);
        } else {
            tube1.setNumber(Integer.toString(hh).substring(0, 1));
            tube2.setNumber(Integer.toString(hh).substring(1, 2));
        }

        // Minutes
        if (mm < 10) {
            tube3.setNumber(0);
            tube4.setNumber(mm);
        } else {
            tube3.setNumber(Integer.toString(mm).substring(0, 1));
            tube4.setNumber(Integer.toString(mm).substring(1, 2));
        }

        // Seconds
        if (ss < 10) {
            tube5.setNumber(0);
            tube6.setNumber(ss);
        } else {
            tube5.setNumber(Integer.toString(ss).substring(0, 1));
            tube6.setNumber(Integer.toString(ss).substring(1, 2));
        }
    }

    @Override
    public void play() {
        TIMER.start();
    }

    @Override
    public void stop() {
        TIMER.stop();
    }
}