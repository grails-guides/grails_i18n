package demo

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import org.springframework.context.MessageSource
import org.springframework.web.servlet.support.RequestContextUtils

class LocaleNavbarTagLib implements GrailsConfigurationAware {

    static namespace = 'navBar'

    static defaultEncodeAs = [taglib: 'none']

    MessageSource messageSource

    List<String> languages

    @Override
    void setConfiguration(Config co) {
        languages = co.getProperty('guide.languages', List) // <1>
    }

    def localeDropdown = { args ->
        String uri = args.uri
        out << '<li class="dropdown">'
        out << '<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Languages <span class="caret"></span></a>'
        out << '<ul class="dropdown-menu">'
        for ( String lang : languages ) {
            String languageCode = "language.$lang"
            def locale = RequestContextUtils.getLocale(request) // <2>
            out << "<li><a href='${uri}?lang=${lang}'>${messageSource.getMessage(languageCode, [] as Object[], null, locale)}</a></li>"
        }
        out << '</ul>'
        out << '</li>'
    }

}
