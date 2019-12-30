package ie.gmit.sw;

public class ParserTest {
    public static void main(String[] args) {
        System.out.println("Building subject db...");
        LanguageDistributionStore store = new LanguageDistributionMap(512);
        SampleParser parser = new FileSampleParser("/home/ronan/Downloads/apache-tomcat-9.0.30/bin/data/wili-2018-Edited.txt", store);
        parser.parseAll();
        System.out.println("Finished. Evaluating test string...");

        String english = "Java is a general-purpose programming language that is class-based, object-oriented, and designed to have as few implementation dependencies as possible. It is intended to let application developers write once, run anywhere, meaning that compiled Java code can run on all platforms that support Java without the need for recompilation.";
        String irish = "Ag gabháil an bóthar abhaile dó, do bhí a aigne agus a intinn trína chéile. I gcaitheamh na slí, níor scar an lámh clé leis an taobh an veist go raibh an sparán laistigh de. Nuair a tháinig se abhaile, bhí a aigne déanta suas. Shuí sé isteach sa cathaoir... thóg úll agus greim den mhín... agus bhí é ag cogaint ... agus chaith sé sparán an Fhir Dhuibh treasna an seomra isteach sa choirnéal – áit ina fhán sé le blianta fada ina dhiadh sin – deannach agus leaba an deamhain alla air. Tharraing sé cuige a chuid leathair agus a chuid chéarach agus a chuid snáithe agus chrom sé ar obair. Nuair a bhí dá fhéire bróg criochnaithe aige, d'imthig sé agus dhíol sé iad agus thug sé luach dhá phúnt leathair abhaile leis, agus ansan luach cheithre bpunt. Ansan do thug sé leis beirt ghréasaí eile ar a bpá lae agus fé cheann tamaill beirt eile. Ba ró ghearr go raibh a ainm in airde sa dúthaigh le feabhas agus le saoire a bhróg.";
        String chinese = "謙関政種索稿用問切必伊後赤車皇健。透需減陸流越古開試欄骨聖納済。環数耕改県本家核特演導察幸無。家道国男史古暮電応政無則力対度持財。円稿前史登紙最義年掛伊出活択採行者芸。稲碁輩説政売要部東勝府野。何交候戻更大禁約読連約育点外。装覧掲奪組除板一載佐紙帰。経発高録最上務向林商質聞載同家女塗度。見究止府図備含在昨思覧場問皇茂";
        String korean = " 세계를 향한 대화, 유니코드로 하십시오. 제10회 유니코드 국제 회의가 1997년 3월 10일부터 12일까지 독일의 마인즈에서 열립니다. 지금 등록하십시오. 이 회의에서는 업계 전반의 전문가들이 함께 모여 다음과 같은 분야를 다룹니다. - 인터넷과 유니코드, 국제화와 지역화, 운영 체제와 응용 프로그램에서 유니코드의 구현, 글꼴, 문자 배열, 다국어 컴퓨팅";
        String french = "Je suis en vacances ! J’en profite pour faire une grasse-matinée et je sors de mon lit à dix heure. Je prends mon petit-déjeuner dans la cuisine. Je mange deux tartines de pain, un grand bol de café et un yaourt aux fruits rouges.";
        String latin = "Confíteor Deo omnipoténti, beátæMaríæ semper Vírgini, beáto MichaéliArchángelo, beáto Joánni Baptístæ,sanctis Apóstolis Petro et Paulo,ómnibus Sanctis, et vobis fratres: quiapeccávi nimis cogitatióne, verbo, etópere:  mea culpa, mea culpa, meamáxima culpa. Ídeo precor beátamMaríam semper Vírginem, beátumMichaélem Archángelum, beátumJoánnem Baptístam, sanctosApóstolos Petrum et Paulum, omnesSanctos, et vos fratres, oráre pro mead Dóminum Deum nostrum";
        String dutch  = "Een ieder heeft recht op onderwijs; het onderwijs zal kosteloos zijn, althans wat het lager en basisonderwijs betreft. Het lager onderwijs zal verplicht zijn. Ambachtsonderwijs en beroepsopleiding zullen algemeen beschikbaar worden gesteld. Hoger onderwijs zal openstaan voor een ieder, die daartoe de begaafdheid bezit.  Het onderwijs zal gericht zijn op de volle ontwikkeling van de menselijke persoonlijkheid en op de versterking van de eerbied voor de rechten van de mens en de fundamentele vrijheden. Het zal het begrip, de verdraagzaamheid en de vriendschap onder alle naties, rassen of godsdienstige groepen bevorderen en het zal de werkzaamheden van de Verenigde Naties voor de handhaving van de vrede steunen. Aan de ouders komt in de eerste plaats het recht toe om de soort van opvoeding en onderwijs te kiezen, welke aan hun kinderen zal worden gegeven.";
        String japanese = "明日は英語のテストだろう、だったら今晩は数学の宿題にかかずらわっちゃだめだ。";
        LanguageDistribution testDist = new HashedLanguageDistribution(512);
        testDist.recordSample(japanese, 3);

        LanguageDetector languageDetector = LanguageDetectorFactory.getInstance().getSimpleLanguageDetector();
        Language closest = languageDetector.findClosestLanguage(testDist, store);
        System.out.println("Closest lang: " + closest.name());
    }
}
