/*
 * Copyright (c) 2016 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package hpcrusher.web.controller;

import hpcrusher.model.Person;
import hpcrusher.repository.PersonRepository;
import hpcrusher.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author David Liebl
 */

@Controller
@Transactional
@RequestMapping(value = "/")
public class HomeController {

    private final PersonRepository personRepository;

    @Autowired
    public HomeController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHomeAsHtml() {
        final ModelAndView modelAndView = new ModelAndView("home");
        final Person loggedInPerson = SecurityService.getLoggedInPerson();
        if (loggedInPerson != null) {
            final String username = loggedInPerson.getUsername();
            if (username != null) {
                modelAndView.addObject("username", username);
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "ranking", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRankingAsHtml() {
        ModelAndView modelAndView = new ModelAndView("ranking");
        final List<Person> personList = personRepository.findAll(new Sort("wins", "username"));
        modelAndView.addObject("persons", personList);
        return modelAndView;
    }

    @RequestMapping(value="/impressum",method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getImpressumAsHtml() {
        return new ModelAndView("impressum");
    }

}
