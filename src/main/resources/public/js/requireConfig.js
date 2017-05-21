
/*
 * Copyright (c) 2017 David Liebl, Martin Geßenich, Sebastian Pettirsch, Christian Rehaag, Volker Mertens
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

requirejs.config({
    baseUrl: '/js',
    wrapShim: true,
    paths: {
        //require.js plugins
        domReady: '3rd/domReady.min',

        // 3rd party
        jquery: '3rd/jquery-3.1.1.min',
        bootstrap: '3rd/bootstrap.min',
        colorpicker: '3rd/bootstrap-colorpicker.min',
        jsRender: '3rd/jsrender.min',
        flipclock: '3rd/flipclock.min',
        datetime: '3rd/moment',
        datepicker: '3rd/bootstrap-datepicker.min',
        dp_language: 'locales/bootstrap-datepicker.de.min',
        stomp: '3rd/stomp.min',
        sockjs: '3rd/sockjs.min',

        // internal use only
        select2: '3rd/select2.min',
        jquery_toast: '3rd/jquery.toast.min',

        // Wrapper
        connnector: 'connector',
        ttToast: 'components/ttToast',
        ttSelect: 'components/ttSelect',
        ttTimer: 'components/ttTimer',
        ttItemTree: 'components/ttItemTree'
    },
    shim: {
        jquery: { "exports": "$"},
        stomp: {"exports": "Stomp"},
        sockjs: {"exports": "SockJS"},
        bootstrap: ['jquery'],
        colorpicker: ['jquery', 'bootstrap'],
        jsRender: ['jquery'],
        datetime: { "exports": "moment"},
        datepicker: ['jquery', 'bootstrap'],
        dp_language: ['jquery', 'datepicker'],

        // internal use only
        select2: ['jquery'],
        jquery_toast: ['jquery'],
        flipclock: ['jquery'],

        // Wrapper
        connector: ['jquery'],
        ttToast: ['jquery', 'jquery_toast'],
        ttSelect: ['jquery', 'select2'],
        ttTimer: ['jquery', 'flipclock'],
        ttItemTree: ['jquery', 'connector']
    }
});
