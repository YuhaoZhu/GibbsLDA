package com.usc;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;

/**
 * Created by zhoutsby on 4/15/15.
 */
public class test {
    public static void main(String[] args) {
//        String str = "’";
//        System.out.println("\u0039");
        String body = "&lt;p&gt;&lt;strong&gt;TLDR&lt;/strong&gt;: Although something of a ‘rule’ for this does exist to say /artʃ/, it has several exceptions handed down to us from a Latinized transliteration of Greek chi where the /k/ sound persists even now.  So you’ll usually guess right, but to be sure, you really have to look it up. &lt;/p&gt;&#xA;&#xA;&lt;hr&gt;&#xA;&#xA;&lt;p&gt;The prefix &lt;em&gt;arch-&lt;/em&gt; meaning the head, chief, principal, or high something, is always pronounced /artʃ/ save in &lt;em&gt;archangel&lt;/em&gt; alone.  &lt;/p&gt;&#xA;&#xA;&lt;p&gt;The other words that have a /k/ there (like &lt;em&gt;architecture&lt;/em&gt; and &lt;em&gt;archipelago&lt;/em&gt; and &lt;em&gt;archaeologist&lt;/em&gt;)  are not created with the prefix &lt;em&gt;arch-&lt;/em&gt;.  (But some were, like &lt;em&gt;archetype&lt;/em&gt;).&lt;/p&gt;&#xA;&#xA;&lt;p&gt;The OED explains what &lt;em&gt;archangel&lt;/em&gt; is an exception to this rule with these words, nothing the parenthesized section at the end:&lt;/p&gt;&#xA;&#xA;&lt;blockquote&gt;&#xA;  &lt;h2&gt;arch-&lt;/h2&gt;&#xA;  &#xA;  &lt;p&gt;&lt;strong&gt;/ɑrtʃ/; exc. in &lt;em&gt;archangel&lt;/em&gt;&lt;/strong&gt;, &lt;i&gt;prefix&lt;/i&gt;: repr. Gr. &lt;i&gt;ἀρχι-, ἀρχ’-&lt;/i&gt;, comb. form of &lt;i&gt;ἀρχ-ός&lt;/i&gt; chief (cogn. w. &lt;i&gt;ἄρχ-ειν&lt;/i&gt; to begin, take the lead), as in &lt;i&gt;ἀρχι-διάκονος&lt;/i&gt; chief-minister, &lt;i&gt;ἀρχι-επίσκοπος&lt;/i&gt; chief-bishop, &lt;i&gt;ἀρχ-άγγελος&lt;/i&gt; chief-angel. Hence in later L. &lt;i&gt;archidiāconus&lt;/i&gt;, &lt;i&gt;archiepiscopus&lt;/i&gt;, &lt;i&gt;archangelus&lt;/i&gt;; in OF. &lt;i&gt;arce-archediacne&lt;/i&gt;, &lt;i&gt;arce-archevesque&lt;/i&gt;, &lt;i&gt;arc-archangele&lt;/i&gt;. (In L. the &lt;i&gt;ch&lt;/i&gt; was treated as &lt;i&gt;c&lt;/i&gt;; hence, in Romanic, it remained = &lt;b&gt;k&lt;/b&gt; in &lt;i&gt;archangelus&lt;/i&gt;; in other words, it became in It. &lt;i&gt;arce-&lt;/i&gt;, &lt;i&gt;arci-&lt;/i&gt;, Pr., Sp., Pg. &lt;i&gt;arce&lt;/i&gt;, OF. &lt;i&gt;arce-&lt;/i&gt;, later &lt;i&gt;arche-&lt;/i&gt;; whence G. &lt;i&gt;erz-&lt;/i&gt;, Du. &lt;i&gt;aarts-&lt;/i&gt;.) &lt;/p&gt;&#xA;&lt;/blockquote&gt;&#xA;&#xA;&lt;p&gt;The OED actually goes on for miles and miles, but the important part about the &lt;em&gt;k&lt;/em&gt; is covered in the quoted part above.  Here’s the next paragraph following just to add more history to the matter:&lt;/p&gt;&#xA;&#xA;&lt;blockquote&gt;&#xA;  &lt;p&gt;In OE. at first translated by &lt;i&gt;héah-&lt;/i&gt; high (&lt;i&gt;héah-diacon&lt;/i&gt;, &lt;i&gt;héah-biscop&lt;/i&gt;, &lt;i&gt;héah-ȩngel&lt;/i&gt;, etc.), but also at length adopted from L. as &lt;i&gt;arce-&lt;/i&gt;, &lt;i&gt;ærce-&lt;/i&gt;, &lt;i&gt;ȩrce-&lt;/i&gt; (? orig. &lt;i&gt;arci-&lt;/i&gt;), in &lt;i&gt;ȩrce-diacon&lt;/i&gt;, &lt;i&gt;ȩrce-biscop&lt;/i&gt;, &lt;i&gt;ȩrce-stól&lt;/i&gt; arch-see, &lt;i&gt;ȩrce-hád&lt;/i&gt; archiepiscopal dignity. The OE. &lt;i&gt;ȩrce-&lt;/i&gt;, &lt;i&gt;arce-&lt;/i&gt;, became in ME. &lt;i&gt;erche-&lt;/i&gt;, &lt;i&gt;arche-&lt;/i&gt;, the latter coinciding with OF. &lt;i&gt;arche-&lt;/i&gt;, whence also &lt;i&gt;archangel&lt;/i&gt; was added. From these, in later times, &lt;i&gt;arch-&lt;/i&gt; became a living formative, prefixable to any name of office. The same happened in med.L. and most mod. langs.; hence many of the Eng. examples, e.g. &lt;i&gt;archduke&lt;/i&gt;, are adaptations of foreign titles. Since the 16th c., &lt;i&gt;arch-&lt;/i&gt; has been freely prefixed to names of agents and appellatives (like &lt;i&gt;arci-&lt;/i&gt; in Ital., and &lt;i&gt;archi-&lt;/i&gt; in French, as &lt;i&gt;archifou&lt;/i&gt;, &lt;i&gt;archipédant&lt;/i&gt;); in a few instances also to appellations of things, and occasionally even to adjectives. Finally, from its faculty of being prefixed to any appellative, &lt;i&gt;arch&lt;/i&gt; has gradually come to be a separate adjective; see prec. word. (In modern literary words from Gr., the prefix is, in Eng., as in all the Romance langs., &lt;b&gt;archi-&lt;/b&gt; q.v.) In pronunciation, the compounds of &lt;i&gt;arch-&lt;/i&gt; have two accents, either of which may be the stronger, according to emphasis, as in &lt;i&gt;right hand&lt;/i&gt;. But established compounds, as &lt;i&gt;archangel&lt;/i&gt;, &lt;i&gt;-bishop&lt;/i&gt;, &lt;i&gt;-deacon&lt;/i&gt;, &lt;i&gt;-duke&lt;/i&gt;, tend to have the main stress on &lt;i&gt;arch-&lt;/i&gt;, especially when they are prefixed to a name, as, the &lt;i&gt;ˈA.rchduke ˈCha.rles&lt;/i&gt;, &lt;i&gt;ˈA.rchbishop ˈCra.nmer&lt;/i&gt;. As a prefix the usual sense is ‘chief, principal, high, leading, prime,’ occasionally ‘first in time, original, initial,’ but in modern use it is chiefly prefixed intensively to words of bad or odious sense, as in &lt;i&gt;arch-traitor&lt;/i&gt;, &lt;i&gt;arch-enemy&lt;/i&gt;, &lt;i&gt;arch-rogue&lt;/i&gt;.&lt;/p&gt;&#xA;&lt;/blockquote&gt;&#xA;&#xA;&lt;hr&gt;&#xA;&#xA;&lt;h2&gt;Edit&lt;/h2&gt;&#xA;&#xA;&lt;p&gt;Besides &lt;em&gt;archangel&lt;/em&gt;, other exceptions include &lt;em&gt;archetype&lt;/em&gt; and &lt;em&gt;archespore&lt;/em&gt;; there are a few others.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;Under its entry for &lt;strong&gt;&lt;em&gt;arch-&lt;/em&gt;&lt;/strong&gt; &lt;em&gt;prefix&lt;/em&gt;, The OED goes on to list 107 words with a leading   &lt;em&gt;arch-&lt;/em&gt; in them, which seem to all follow its rule that these be pronounced the normal /arch/ way:&lt;/p&gt;&#xA;&#xA;&lt;blockquote&gt;&#xA;  &lt;p&gt;arch‑agitator, arch‑antiquary, arch‑apostle, arch‑artist, arch‑beacon,&#xA;  arch‑beadle, arch‑botcher, arch‑boutefeu, arch‑buffoon, arch‑builder,&#xA;  arch‑butler, arch‑charlatan, arch‑cheater, arch‑chemic, arch‑chief,&#xA;  arch‑christendom, arch‑city, arch‑consoler, arch‑conspirator,&#xA;  arch‑corrupter, arch‑cosener, arch‑criminal, arch‑critic, arch‑dapifer,&#xA;  arch‑deceiver, arch‑defender, arch‑depredator, arch‑despot, arch‑devil,&#xA;  arch‑diplomatist, arch‑dissembler, arch‑disturber, arch‑divine,&#xA;  arch‑dogmatist, arch‑dolt, arch‑earl, arch‑essence, arch‑exorcist,&#xA;  arch‑father, arch‑felon, arch‑fire, arch‑fool, arch‑form, arch‑founder,&#xA;  arch‑friend, arch‑god, arch‑gomeril, arch‑heart, arch‑host, arch‑house,&#xA;  arch‑humbug, arch‑hypocrite, arch‑infamy, arch‑informer, arch‑jockey,&#xA;  arch‑knave, arch‑leader, arch‑lexicographer, arch‑liar, arch‑machine,&#xA;  arch‑mediocrity, arch‑messenger, arch‑metaphysician, arch‑mistress,&#xA;  arch‑mock, arch‑mockery, arch‑mystagogue, arch‑noble, arch‑pall,&#xA;  arch‑philosopher, arch‑piece, arch‑pillar, arch‑plagiary, arch‑player,&#xA;  arch‑plotter, arch‑plunderer, arch‑politician, arch‑practice,&#xA;  arch‑pretender, arch‑prophet, arch‑protestant, arch‑puritan,&#xA;  arch‑rationalist, arch‑representative, arch‑robber, arch‑rogue,&#xA;  arch‑saint, arch‑scandalmonger, arch‑sceptic, arch‑scoundrel,&#xA;  arch‑sea, arch‑seducer, arch‑see, arch‑semipelagian, arch‑sin,&#xA;  arch‑snake, arch‑spy, arch‑synagogue, arch‑tempter, arch‑traitor,&#xA;  arch‑turncoat, arch‑tyrant, arch‑urger, arch‑vagabond, arch‑wag,&#xA;  arch‑wench, arch‑worker.&lt;/p&gt;&#xA;&lt;/blockquote&gt;&#xA;&#xA;&lt;p&gt;Unlike words beginning with the  &lt;em&gt;arch‑&lt;/em&gt; prefix, which have a /tʃ/, those that have an &lt;em&gt;arch(a)eo‑&lt;/em&gt; or &lt;em&gt;archi‑&lt;/em&gt; prefix, or an &lt;em&gt;‑archy&lt;/em&gt; suffix or &lt;em&gt;‑arche&lt;/em&gt; suffix, have a /k/ there.  &lt;/p&gt;&#xA;&#xA;&lt;p&gt;The &lt;em&gt;archi-&lt;/em&gt; prefix the OED explains as:&lt;/p&gt;&#xA;&#xA;&lt;blockquote&gt;&#xA;  &lt;p&gt;L. &lt;em&gt;archi-&lt;/em&gt;, Gr. &lt;em&gt;ἀρχι-&lt;/em&gt;: see &lt;em&gt;arch-&lt;/em&gt;. This form of the prefix is retained in words taken in modern times from Gr. or L., directly or through mod.Fr., and in compounds formed on the model of these. Hence it is sometimes found in the adjectives, etc. belonging to substantives, which, from their earlier introduction, have themselves the form &lt;em&gt;arch-&lt;/em&gt;, as &lt;em&gt;archdeacon, archidiaconal, archbishop, archiepiscopal&lt;/em&gt;. Some words have both forms, as &lt;em&gt;archi-presbyter, arch-presbyter&lt;/em&gt;.&lt;/p&gt;&#xA;&lt;/blockquote&gt;&#xA;&#xA;&lt;p&gt;In the list below, you indeed see that &lt;em&gt;archi-presbyter&lt;/em&gt; it gives with a /k/ in it, but the other one with the /ʧ/ affricate.  There are more of the /k/ ones, but in a few odd cases it is difficult to predict.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;I’ve grepped out (grabbed) all the OED headwords with ‹arch› in them and listed their IPA next to them.  I prefix each headword with a /k/ or /ʧ/ to make it easier to quickly scan for exceptions; a few unassimilated French terms have just /ʃ/.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;(I’ve manually trimmed some of the false positives such as &lt;em&gt;searched&lt;/em&gt;.)&lt;/p&gt;&#xA;&#xA;&lt;p&gt;I have also rewritten IPA /ɑ˞ː/ as /ar/ to make it clearer for rhotic speakers, who will make that an &lt;em&gt;r&lt;/em&gt;-colored vowel instead. If you’re an arrhotic speaker, you will ignore it anyway. :)&lt;/p&gt;&#xA;&#xA;&lt;pre&gt;&lt;code&gt;     1  k anarchal (adj.)                /əˈnarkəl/&#xA;     2  k anarchial (adj.)               /əˈnarkɪəl/&#xA;     3  k anarchic (adj.)                /əˈnarkɪk/&#xA;     4  k anarchical (adj.)              /əˈnarkɪkəl/&#xA;     5  k anarchism (n.)                 /ˈænɚkɪz(ə)m/&#xA;     6  k anarchist (adj.)               /ˈænɚkɪst/&#xA;     7  k anarchistic (adj.)             /ænarˈkɪstɪk/&#xA;     8  k anarchize (v.)                 /ˈænɚkaɪz/&#xA;     9  k anarcho-syndicalism (n.)       /æˌnarkoʊ/&#xA;    10  k anarchy (n.)                   /ˈænɚkɪ/&#xA;    11  k antarchism (n.)                /ˈæntɚkɪz(ə)m/&#xA;    12  k antimonarchical (adj.)         /ˌæntɪmoʊˈnarkɪkəl/&#xA;    13  k antimonarchist (n.)            /æntɪˈmɒnɚkɪst/&#xA;    14  ʧ arch- (prefix)                 /artʃ/&#xA;    15  k archæbacterium (adj.)          /arkɪbækˈtɪərɪəm/&#xA;    16  k archæo- (adj.)                 /ˌarkiːoʊ-/&#xA;    17  k archæoastronomy (adj.)         /ˌarkiːoʊəˈstrɒnəmɪ/&#xA;    18  k archæocyte (n.)                /ˈarkiːəsaɪt/&#xA;    19  k archæographical (adj.)         /ˌarkiːoʊˈgræfɪkəl/&#xA;    20  k archæography (n.)              /arkiːˈɒgrəfɪ/&#xA;    21  k archæologer (n.)               /arkiːˈɒlədʒə(r)/&#xA;    22  k archæologian (n.)              /ˌarkiːoʊˈloʊdʒɪən/&#xA;    23  k archæologic (adj.)             /ˌarkiːoʊˈlɒdʒɪk/&#xA;    24  k archæologist (n.)              /arkiːˈɒlədʒɪst/&#xA;    25  k archæologize (v.)              /arkiːˈɒlədʒaɪz/&#xA;    26  k archæologue (n.)               /ˈarkiːəlɒg/&#xA;    27  k archæology (n.)                /arkiːˈɒlədʒɪ/&#xA;    28  k archæomagnetism (adj.)         /ˌarkiːoʊˈmægnətɪz(ə)m/&#xA;    29  k archæometry (adj.)             /arkiːˈɒmɪtrɪ/&#xA;    30  k archæopteryx (n.)              /arkiːˈɒptərɪks/&#xA;    31  k archaic (adj.)                 /arˈkeɪɪk/&#xA;    32  k archaicism (n.)                /arˈkeɪɪsɪz(ə)m/&#xA;    33  k archaicist (adj.)              /arˈkeɪɪsɪst/&#xA;    34  k archaism (n.)                  /ˈarkeɪɪz(ə)m/&#xA;    35  k archaist (n.)                  /ˈarkeɪɪst/&#xA;    36  k archaistic (adj.)              /arkeɪˈɪstɪk/&#xA;    37  k archaistically (adv.)          /arkeɪˈɪstɪkəlɪ/&#xA;    38  k archaize (v.)                  /ˈarkeɪaɪz/&#xA;    39  k archangel (n.)                 /ˈarkˈeɪndʒəl/&#xA;    40  k archangelic (adj.)             /arkænˈdʒɛlɪk/&#xA;    41  k archangelship (n.)             /arkˈeɪndʒəlʃɪp/&#xA;    42  ʧ archbishop (adj.)              /ˈartʃˈbɪʃəp/&#xA;    43  ʧ archbishopric (n.)             /artʃˈbɪʃəprɪk/&#xA;    44  ʧ archdeacon (n.)                /ˈartʃˈdiːkən/&#xA;    45  ʧ archdeaconry (n.)              /artʃˈdiːkənrɪ/&#xA;    46  ʧ archdiocese (n.)               /ˈartʃˈdaɪəsiːs/&#xA;    47  ʧ archducal (adj.)               /artʃˈdjuːkəl/&#xA;    48  ʧ archduchess (n.)               /ˈartʃˈdʌtʃɪs/&#xA;    49  ʧ archduchy (n.)                 /ˈartʃˈdʌtʃɪ/&#xA;    50  ʧ archduke (n.)                  /ˈartʃˈdjuːk/&#xA;    51  ʧ archdukedom (n.)               /artʃˈdjuːkdəm/&#xA;    52  k archebiosis (n.)               /arkɪbaɪˈoʊsɪs/&#xA;    53  ʧ arched (ppl. a.)               /artʃt/&#xA;    54  ʧ archegay (n.)                  /ˈartʃɪgɑːɪ/&#xA;    55  k archegonial (adj.)             /arkɪˈgoʊnɪəl/&#xA;    56  k archegonium (n.)               /arkɪˈgoʊnɪəm/&#xA;    57  k archelogy (n.)                 /arˈkɛlədʒɪ/&#xA;    58  k archenteron (adj.)             /arˈkɛntərɒn/&#xA;    59  ʧ archer (n.)                    /ˈartʃə(r)/&#xA;    60  ʧ archeress (n.)                 /ˈartʃərɪs/&#xA;    61  ʧ archership (n.)                /ˈartʃɚʃɪp/&#xA;    62  ʧ archery (n.)                   /ˈartʃərɪ/&#xA;    63  ʧ arches (n.)                    /ˈartʃɪz/&#xA;    64  k archespore (adj.)              /ˈarkɪspɔə(r)/&#xA;    65  k archetypal (adj.)              /arˈkɛtɪpəl/&#xA;    66  k archetype (adj.)               /ˈarkɪtaɪp/&#xA;    67  k archetypist (n.)               /ˈarkɪtaɪpɪst/&#xA;    68  k archeus (n.)                   /arˈkiːəs/&#xA;    69  ʧ arch-fiend (n.)                /ˈɑːrtʃˈfiːnd/&#xA;    70  ʧ arch-flamen (n.)               /ˈartʃˈfleɪmɛn/&#xA;    71  ʧ arch-foe (n.)                  /ˈartʃˈfoʊ/&#xA;    72  k archi- (pref.)                 /ˌarkɪ-/&#xA;    73  k archiater (n.)                 /arkɪˈeɪtə(r)/&#xA;    74  k archibenthal (adj.)            /arkɪˈbɛnθəl/&#xA;    75  k archiblast (adj.)              /ˈarkɪblæst/&#xA;    76  k archicerebrum (n.)             /arkɪˈsɛrɪbrəm/&#xA;    77  k archidiaconal (adj.)           /ˌarkɪdaɪˈækənəl/&#xA;    78  k archiepiscopacy (n.)           /ˌarkɪɪˈpɪskəpəsɪ/&#xA;    79  k archiepiscopal (adj.)          /-skəpəl/&#xA;    80  k archiepiscopate (adj.)         /-skəpeɪt/&#xA;    81  k archigony (n.)                 /arˈkɪgənɪ/&#xA;    82  ʧ archil (n.)                    /ˈartʃɪl/&#xA;    83  k archimage (n.)                 /ˈarkɪmeɪdʒ/&#xA;    84  k archimandrite (n.)             /arkɪˈmændraɪt/&#xA;    85  k archimime (n.)                 /ˈarkɪˌmaɪm/&#xA;    86  ʧ arching (vbl. sb.)             /ˈartʃɪŋ/&#xA;    87  ʧ arching (ppl. a.)              /ˈartʃɪŋ/&#xA;    88  k archipallium (adj.)            /arkɪˈpælɪəm/&#xA;    89  k archipelagian (n.)             /ˌarkɪpɪˈleɪdʒ(ɪ)ən/&#xA;    90  k archipelagic (adj.)            /-ˈlædʒɪk/&#xA;    91  k archipelago (n.)               /arkɪˈpɛləgoʊ/&#xA;    92  k archipelagoed (pa. pple.)      /arkɪˈpɛləgoʊd/&#xA;    93  k archiphoneme (adj.)            /ˌarkɪˈfoʊniːm/&#xA;    94  k archipresbyteral (adj.)        /ˌarkɪprɛzˈbɪtərəl/&#xA;    95  k archipresbyterate (n.)         /ˌarkɪprɛzˈbɪtərət/&#xA;    96  k architect (n.)                 /ˈarkɪtɛkt/&#xA;    97  k architective (adj.)            /ˈarkɪtɛktɪv/&#xA;    98  k architectonic (adj.)           /ˌarkɪtɛkˈtɒnɪk/&#xA;    99  k architectress (n.)             /ˈarkɪtɛktrɪs/&#xA;   100  k architectural (adj.)           /arkɪˈtɛktjʊərəl/&#xA;   101  k architecturalist (n.)          /-ˌɪst/&#xA;   102  k architecturalization (n.)      /-aɪˈzeɪʃən/&#xA;   103  k architecturalize (v.)          /-ˌaɪz/&#xA;   104  k architecture (n.)              /ˈarkɪtɛktjʊə(r)/&#xA;   105  k architrave (n.)                /ˈarkɪtreɪv/&#xA;   106  k archival (adj.)                /ˈarkɪvəl/&#xA;   107  k archive (pl.)                  /ˈarkaɪv/&#xA;   108  k archive (v.)                   /ˈarkaɪv/&#xA;   109  k archived (ppl.)                /ˈarkaɪvd/&#xA;   110  k archivist (n.)                 /ˈarkɪvɪst/&#xA;   111  k archivolt (n.)                 /ˈarkɪvoʊlt/&#xA;   112  ʧ archlet (n.)                   /ˈartʃlɪt/&#xA;   113  ʧ archlute (n.)                  /ˈartʃˈljuːt/&#xA;   114  ʧ archly (adv.)                  /ˈartʃlɪ/&#xA;   115  ʧ archness (adj.)                /ˈartʃnɪs/&#xA;   116  k archology (n.)                 /arˈkɒlədʒɪ/&#xA;   117  k archon (n.)                    /ˈarkən/&#xA;   118  k archonship (n.)                /ˈarkənʃɪp/&#xA;   119  k archontate (n.)                /ˈarkənteɪt/&#xA;   120  k archontic (adj.)               /arˈkɒntɪk/&#xA;   121  k archosaur (n.)                 /ˈarkoʊsɔː(r)/&#xA;   122  k archosaurian (adj.)            /arkoʊˈsɔːrɪən/&#xA;   123  ʧ archpresbyter (n.)             /ˈartʃˈprɛzbɪtə(r)/&#xA;   124  ʧ archpriest (n.)                /ˈartʃˈpriːst/&#xA;   125  ʧ arch-thief (n.)                /ˈartʃˈθiːf/&#xA;   126  ʧ archway (n.)                   /ˈartʃweɪ/&#xA;   127  ʧ archwise (adv.)                /ˈartʃwaɪz/&#xA;   128  ʧ archy (adj.)                   /ˈartʃɪ/&#xA;   129  k autarchic (adj.)               /ɔːˈtarkɪk/&#xA;   130  k autarchy (n.)                  /ˈɔːtɚkɪ/&#xA;   131  k barchan (n.)                   /barˈkɑːn/&#xA;   132  k biarchy (pref.)                /ˈbaɪarkɪ/&#xA;   133  k chiliarchy (n.)                /ˈkɪlɪarkɪ/&#xA;   134  k decarchy (n.)                  /ˈdɛkɚkɪ/&#xA;   135  ʧ deep-searching (ppl. a.)       /ˈdiːpˈsɝːtʃɪŋ/&#xA;   136  ʃ démarche (n.)                  /demarʃ/&#xA;   137  k demarchy (n.)                  /ˈdiːmarkɪ/&#xA;   138  k diabolarchy (n.)               /daɪˈæbəlarkɪ/&#xA;   139  k diarchal (adj.)                /daɪˈarkəl/&#xA;   140  k diarchic (adj.)                /daɪˈarkɪk/&#xA;   141  k diarchy (n.)                   /ˈdaɪarkɪ/&#xA;   142  k dodecarchy (n.)                /ˈdoʊdɪkarkɪ/&#xA;   143  k duarchy (n.)                   /ˈdjuːɚkɪ/&#xA;   144  k eparchy (n.)                   /ˈɛpɚkɪ/&#xA;   145  k ethnarchy (n.)                 /ˈɛθnarkɪ/&#xA;   146  k ethnoarchæology (adj.)         /ˌɛθnoʊarkiːˈɒlədʒɪ/&#xA;   147  k exarchate (n.)                 /ˈɛksarkeɪt/&#xA;   148  k Exarchist (n.)                 /ˈɛksarkɪst/&#xA;   149  ʃ franc-archer (adj.)            /frɑ̃karʃe/&#xA;   150  k gerontarchical (adj.)          /ˌdʒɛrənˈtarkɪkəl/&#xA;   151  k gynarchy (n.)                  /ˈdʒaɪnarkɪ/&#xA;   152  k hamarchy (n.)                  /ˈhæmɚkɪ/&#xA;   153  k hecatarchy (n.)                /ˈhɛkətarkɪ/&#xA;   154  k hecatontarchy (n.)             /hɛkəˈtɒntarkɪ/&#xA;   155  k heptarchy (n.)                 /ˈhɛptarkɪ/&#xA;   156  k hierarchal (adj.)              /haɪəˈrarkəl/&#xA;   157  k hierarchic (adj.)              /haɪəˈrarkɪk/&#xA;   158  k hierarchist (n.)               /ˈhaɪərarkɪst/&#xA;   159  k hierarchy (gen.)               /ˈhaɪərarkɪ/&#xA;   160  k ichthyarchy (n.)               /ˈɪkθɪarkɪ/&#xA;   161  ʧ inarching (vbl. sb.)           /ɪnˈartʃɪŋ/&#xA;   162  k kritarchy (n.)                 /ˈkrɪtarkɪ/&#xA;   163  ʧ larchen (adj.)                 /ˈlartʃən/&#xA;   164  k marchantia (n.)                /marˈkæntɪə/&#xA;   165  ʧ marcher (n.)                   /ˈmartʃə(r)/&#xA;   166  ʧ marcher (v.)                   /ˈmartʃə(r)/&#xA;   167  k marchesa (n.)                  /marˈkeza/&#xA;   168  k marchese (n.)                  /marˈkeze/&#xA;   169  ʧ marching (vbl. sb.)            /ˈmartʃɪŋ/&#xA;   170  ʧ marching (ppl. a.)             /ˈmartʃɪŋ/&#xA;   171  k marchioness (n.)               /ˈmarʃənɪs/&#xA;   172  k matriarchal (adj.)             /meɪtrɪˈarkəl/&#xA;   173  k matriarchate (n.)              /meɪtrɪˈarkət/&#xA;   174  k matriarchy (n.)                /ˈmeɪtrɪarkɪ/&#xA;   175  k menarche (n.)                  /məˈnarkiː/&#xA;   176  k monarchal (adj.)               /məˈnarkəl/&#xA;   177  k monarchess (n.)                /ˈmɒnɚkɪs/&#xA;   178  k monarchial (adj.)              /məˈnarkɪəl/&#xA;   179  k Monarchian (n.)                /məˈnarkɪən/&#xA;   180  k monarchic (adj.)               /məˈnarkɪk/&#xA;   181  k monarchical (adj.)             /məˈnɑːrkɪkəl/&#xA;   182  k monarchism (n.)                /ˈmɒnɚkɪz(ə)m/&#xA;   183  k monarchist (n.)                /ˈmɒnɚkɪst/&#xA;   184  k monarchistic (adj.)            /mɒnɚˈkɪstɪk/&#xA;   185  k monarchize (v.)                /ˈmɒnɚkaɪz/&#xA;   186  k monarcho- (adj.)               /məˈnarkoʊ/&#xA;   187  k monarchy (adj.)                /ˈmɒnɚkɪ/&#xA;   188  k navarchy (n.)                  /ˈneɪvarkɪ/&#xA;   189  k nomarchy (n.)                  /ˈnɒmarkɪ/&#xA;   190  k octarchy (n.)                  /ˈɒktarkɪ/&#xA;   191  k oligarchal (adj.)              /ˈɒlɪgarkəl/&#xA;   192  k oligarchic (adj.)              /ɒlɪˈgarkɪk/&#xA;   193  k oligarchical (adj.)            /ɒlɪˈgarkɪkəl/&#xA;   194  k oligarchism (n.)               /ˈɒlɪgarkɪz(ə)m/&#xA;   195  k oligarchization (n.)           /ɒlɪgarkaɪˈzeɪʃən/&#xA;   196  k oligarchize (v.)               /ˈɒlɪgarkaɪz/&#xA;   197  k oligarchy (n.)                 /ˈɒlɪgarkɪ/&#xA;   198  k panarchy (n.)                  /ˈpænɚkɪ/&#xA;   199  k paparchy (adj.)                /ˈpeɪpɚkɪ/&#xA;   200  k patriarchal (adj.)             /peɪtrɪˈarkəl/&#xA;   201  k patriarchate (n.)              /ˈpeɪtrɪarkət/&#xA;   202  k patriarchdom (n.)              /ˈpeɪtrɪarkdəm/&#xA;   203  k patriarchess (n.)              /ˈpeɪtrɪarkɪs/&#xA;   204  k patriarchic (adj.)             /peɪtrɪˈarkɪk/&#xA;   205  k patriarchical (adj.)           /peɪtrɪˈarkɪkəl/&#xA;   206  k patriarchism (n.)              /ˈpeɪtrɪarkɪz(ə)m/&#xA;   207  k Patriarchist (n.)              /ˈpeɪtrɪarkɪst/&#xA;   208  k patriarchy (n.)                /ˈpeɪtrɪarkɪ/&#xA;   209  k pentarchy (n.)                 /ˈpɛntɚkɪ/&#xA;   210  k Petrarchal (adj.)              /pɪˈtrarkəl/&#xA;   211  k Plutarchian (adj.)             /pluːˈtarkɪən/&#xA;   212  k plutarchy (n.)                 /ˈpluːtɚkɪ/&#xA;   213  k pollarchy (n.)                 /ˈpɒlɚkɪ/&#xA;   214  k polyarchic (adj.)              /pɒlɪˈarkɪk/&#xA;   215  k polyarchism (n.)               /ˈpɒlɪarkɪz(ə)m/&#xA;   216  k polyarchy (n.)                 /ˈpɒlɪarkɪ/&#xA;   217  k pubarche (n.)                  /pjuːˈbɑrkiː/&#xA;   218  k squirearchal (adj.)            /skwaɪəˈrarkəl/&#xA;   219  k squirearchy (n.)               /ˈskwaɪərarkɪ/&#xA;   220  k stratarchy (n.)                /ˈstrætarkɪ/&#xA;   221  k synarchy (n.)                  /ˈsɪnarkɪ/&#xA;   222  k tetradarchy (n.)               /ˈtɛtrədarkɪ/&#xA;   223  k tetrarchate (n.)               /ˈtɛtrɚkeɪt/&#xA;   224  k tetrarchic (adj.)              /tɪˈtrarkɪk/&#xA;   225  k tetrarchical (adj.)            /tɪˈtrarkɪkəl/&#xA;   226  k tetrarchy (n.)                 /ˈtɛtrarkɪ/&#xA;   227  k thearchic (adj.)               /θiːˈarkɪk/&#xA;   228  k thearchy (n.)                  /ˈθiːɚkɪ/&#xA;   229  k timarchy (n.)                  /ˈtaɪma˞ːkɪ/&#xA;   230  k toparchy (n.)                  /ˈtɒpɚkɪ/&#xA;   231  k triarchy (n.)                  /ˈtraɪɚkɪ/&#xA;   232  k trierarchal (adj.)             /ˈtraɪərarkəl/&#xA;   233  k trierarchy (n.)                /ˈtraɪərarkɪ/&#xA;&lt;/code&gt;&lt;/pre&gt;&#xA;";
        String text = Jsoup.parse(StringEscapeUtils.unescapeHtml4(body)).text();
        System.out.println(text);
    }
}
