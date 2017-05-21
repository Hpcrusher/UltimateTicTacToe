/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package timetakers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author David Liebl
 */

@SpringBootApplication
@EnableWebSecurity
@Configuration
public class UltimateTicTacToeApplication {

    public static Boolean DISABLE_FRAME_OPTIONS = false;

    public static void main(String[] args) {
        ArrayList<String> arguments = new ArrayList<>(args.length + 1);
        Collections.addAll(arguments, args);

        for (int i = 0; i < arguments.size(); i++) {
            String option = arguments.get(i);
            if (option.contains("DISABLE-X-FRAME-OPTIONS=")) {
                DISABLE_FRAME_OPTIONS = Boolean.parseBoolean(option.substring(option.indexOf("=") + 1));
                arguments.remove(i);
            }
        }

        arguments.add("--spring.config.location=file:" + System.getProperty("user.home") + File.separator);
        SpringApplication.run(UltimateTicTacToeApplication.class, arguments.toArray(new String[arguments.size()]));
    }

}
