package demo

import grails.test.mixin.TestFor
import org.springframework.context.MessageSource
import spock.lang.Specification

@TestFor(LocaleNavbarTagLib)
class LocaleNavbarTagLibSpec extends Specification {

    void "LocaleNavbarTagLib method localeDropdown renders"() {
        given:
        def uri = '/books'
        def languages = [[code: 'en', msg: 'English'],
                         [code: 'es', msg: 'Spanish'],
                         [code: 'it', msg: 'Italian'],
                         [code: 'de', msg: 'German']]

        when:
        def expected = """
<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Languages <span class="caret"></span></a>
    <ul class="dropdown-menu">
"""
        languages.each { Map m ->
            expected += "<li><a href='${uri}?lang=${m.code}'>${m.msg}</a></li>"
        }
    expected += """        
    </ul>
</li>
"""
        tagLib.languages = languages.collect { it.code }
        tagLib.messageSource = Stub(MessageSource) {
            getMessage('language.en', [] as Object[], null, _) >> languages.find { it.code == 'en'}.msg
            getMessage('language.es', [] as Object[], null, _) >> languages.find { it.code == 'es'}.msg
            getMessage('language.it', [] as Object[], null, _) >> languages.find { it.code == 'it'}.msg
            getMessage('language.de', [] as Object[], null, _) >> languages.find { it.code == 'de'}.msg
        }
        def result = applyTemplate('<navBar:localeDropdown uri="/books"/>')

        then:
        cleanUpString(result) == cleanUpString(expected)
    }

    String cleanUpString(String str) {
        str.replaceAll('\n','').replaceAll(' ', '')
    }
}
