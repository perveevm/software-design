package ru.perveev.mock.api;

import org.junit.jupiter.api.Test;
import ru.perveevm.mock.api.VkAPIResponseParser;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class VkAPIResponseParserTest {
    private final VkAPIResponseParser parser = new VkAPIResponseParser();

    private static final String REAL_RESPONSE = """
            {
            "response": {
            "items": [{
            "id": 55128,
            "date": 1640552018,
            "owner_id": -102759215,
            "from_id": -102759215,
            "post_type": "post",
            "text": "Помощница замминистра образования Украины привлекла к себе внимание СМИ из-за глубины декольте
                        
            27-летняя Кристина Тишкун на встречу с мэром Львова надела облегающее черное платье с глубоким вырезом.
            Фото Тишкун разместил паблик Лента.ру/ВКонтакте. Как уточняется, справа от помощницы замминистра образования Украины стоит мэр Львова.
            Источник: mos.news
                        
            #mosnews #украина #образование #декольте",
            "marked_as_ads": 0,
            "attachments": [{
            "type": "photo",
            "photo": {
            "album_id": -7,
            "date": 1640552018,
            "id": 457285997,
            "owner_id": -102759215,
            "access_key": "b02b7e0f681a946267",
            "post_id": 55128,
            "sizes": [{
            "height": 130,
            "url": "https://sun9-40.u...HKlY&type=album",
            "type": "m",
            "width": 130
            }, {
            "height": 130,
            "url": "https://sun9-40.u...HKlY&type=album",
            "type": "o",
            "width": 130
            }, {
            "height": 200,
            "url": "https://sun9-40.u...Nf0U&type=album",
            "type": "p",
            "width": 200
            }, {
            "height": 320,
            "url": "https://sun9-40.u...Hwrg&type=album",
            "type": "q",
            "width": 320
            }, {
            "height": 510,
            "url": "https://sun9-40.u...QduM&type=album",
            "type": "r",
            "width": 510
            }, {
            "height": 75,
            "url": "https://sun9-40.u...ti2Q&type=album",
            "type": "s",
            "width": 75
            }, {
            "height": 604,
            "url": "https://sun9-40.u...g0BA&type=album",
            "type": "x",
            "width": 604
            }, {
            "height": 700,
            "url": "https://sun9-40.u...4du8&type=album",
            "type": "y",
            "width": 700
            }],
            "text": "",
            "user_id": 100,
            "has_tags": false
            }
            }],
            "post_source": {
            "platform": "iphone",
            "type": "api"
            },
            "comments": {
            "can_post": 1,
            "count": 0,
            "groups_can_post": true
            },
            "likes": {
            "can_like": 1,
            "count": 0,
            "user_likes": 0,
            "can_publish": 1
            },
            "reposts": {
            "count": 0,
            "user_reposted": 0
            },
            "views": {
            "count": 12
            },
            "is_favorite": false,
            "donut": {
            "is_donut": false
            },
            "short_text_rate": 0.8
            }, {
            "id": 14051,
            "date": 1640551826,
            "owner_id": 32363369,
            "from_id": 32363369,
            "post_type": "post",
            "text": "Профессор МФТИ Алексей Савватеев и независимый эксперт, спикер по теме искусственного интеллекта Игорь Шнуренко о том, кто и как прямо сейчас уничтожает школу, какой свежий документ подписал премьер Михаил Мишустин
            и можно ли ещё всё исправить?
            
            #образование #наука",
            "attachments": [{
            "type": "video",
            "video": {
            "access_key": "6e8cb09ca5d826f2df",
            "can_comment": 1,
            "can_like": 1,
            "can_repost": 1,
            "can_subscribe": 1,
            "can_add_to_faves": 1,
            "can_add": 1,
            "comments": 0,
            "date": 1640551826,
            "duration": 2916,
            "image": [{
            "url": "https://sun9-39.u...Q5w/8bKvYaZf6g4.jpg",
            "width": 130,
            "height": 96,
            "with_padding": 1
            }, {
            "url": "https://sun9-64.u...viA/gASd7g854_U.jpg",
            "width": 160,
            "height": 120,
            "with_padding": 1
            }, {
            "url": "https://sun9-88.u...G6g/BtqBzEV700U.jpg",
            "width": 320,
            "height": 240,
            "with_padding": 1
            }, {
            "url": "https://sun9-30.u...QWQ/bAfw3gVi4aI.jpg",
            "width": 800,
            "height": 450,
            "with_padding": 1
            }],
            "id": 456242272,
            "owner_id": 32363369,
            "title": "Гигантский образовательный эксперимент. Сколько ещё продержится школа? И. Шнуренко, А. Савватеев",
            "is_favorite": false,
            "track_code": "video_2298d5dfbaCahoxh0piT4dEoRRsyr3avoo1CJy3w-xcg9S0sfI1Zi_aFi2fUmqXYjhxwLQCbRJ2Vv0FD",
            "type": "video",
            "views": 1,
            "local_views": 0,
            "platform": "YouTube"
            }
            }],
            "post_source": {
            "type": "vk"
            },
            "comments": {
            "can_post": 0,
            "count": 0,
            "groups_can_post": true
            },
            "likes": {
            "can_like": 1,
            "count": 0,
            "user_likes": 0,
            "can_publish": 1
            },
            "reposts": {
            "count": 0,
            "user_reposted": 0
            },
            "views": {
            "count": 3
            },
            "is_favorite": false,
            "donut": {
            "is_donut": false
            },
            "short_text_rate": 0.8
            }, {
            "id": 324404,
            "date": 1640550300,
            "owner_id": -50496202,
            "from_id": -50496202,
            "post_type": "post",
            "text": "Сocтaв cлoвa &#128522;
            ТЫ Ж УЧИТЕЛЬ - Это крупнейшее сообщество учителей России!&#128104;&#8205;&#127891;&#128105;&#8205;&#127891;
            &#9989;Мы делимся методическими разработками, Планами, рабочими программами.
            &#9989;Мы делаем труд учителя легче.
            &#9989;Мы проводим полезные вебинары.
            &#9989;Мы делаем профессию учителя значимой.
            Мы - это люди! Люди, которые посвятили свою жизнь образованию! Спасибо за ваше участие в жизни сообщества!
            #тыжучитель #учитель #школа #образование",
            "marked_as_ads": 0,
            "attachments": [{
            "type": "photo",
            "photo": {
            "album_id": -7,
            "date": 1639823130,
            "id": 457376161,
            "owner_id": -50496202,
            "access_key": "2d1f4ee988b0c05456",
            "sizes": [{
            "height": 106,
            "url": "https://sun9-26.u...kM-Q&type=album",
            "type": "m",
            "width": 130
            }, {
            "height": 106,
            "url": "https://sun9-26.u...kM-Q&type=album",
            "type": "o",
            "width": 130
            }, {
            "height": 163,
            "url": "https://sun9-26.u...hMxc&type=album",
            "type": "p",
            "width": 200
            }, {
            "height": 260,
            "url": "https://sun9-26.u...UiSw&type=album",
            "type": "q",
            "width": 320
            }, {
            "height": 415,
            "url": "https://sun9-26.u...OHOM&type=album",
            "type": "r",
            "width": 510
            }, {
            "height": 61,
            "url": "https://sun9-26.u...ENV4&type=album",
            "type": "s",
            "width": 75
            }, {
            "height": 431,
            "url": "https://sun9-26.u..._Uno&type=album",
            "type": "x",
            "width": 530
            }],
            "text": "",
            "user_id": 100,
            "has_tags": false
            }
            }, {
            "type": "photo",
            "photo": {
            "album_id": -7,
            "date": 1639823130,
            "id": 457376162,
            "owner_id": -50496202,
            "access_key": "dfd4abc4e87c2f29a9",
            "sizes": [{
            "height": 89,
            "url": "https://sun9-20.u...ERRQ&type=album",
            "type": "m",
            "width": 130
            }, {
            "height": 89,
            "url": "https://sun9-20.u...ERRQ&type=album",
            "type": "o",
            "width": 130
            }, {
            "height": 137,
            "url": "https://sun9-20.u...Vkgs&type=album",
            "type": "p",
            "width": 200
            }, {
            "height": 219,
            "url": "https://sun9-20.u...-c4o&type=album",
            "type": "q",
            "width": 320
            }, {
            "height": 350,
            "url": "https://sun9-20.u...qYmg&type=album",
            "type": "r",
            "width": 510
            }, {
            "height": 51,
            "url": "https://sun9-20.u...pOgg&type=album",
            "type": "s",
            "width": 75
            }, {
            "height": 414,
            "url": "https://sun9-20.u...JdOU&type=album",
            "type": "x",
            "width": 604
            }],
            "text": "",
            "user_id": 100,
            "has_tags": false
            }
            }, {
            "type": "photo",
            "photo": {
            "album_id": -7,
            "date": 1639823130,
            "id": 457376163,
            "owner_id": -50496202,
            "access_key": "38ce6b86f5a957afbe",
            "sizes": [{
            "height": 92,
            "url": "https://sun9-43.u...PSEE&type=album",
            "type": "m",
            "width": 130
            }, {
            "height": 92,
            "url": "https://sun9-43.u...PSEE&type=album",
            "type": "o",
            "width": 130
            }, {
            "height": 142,
            "url": "https://sun9-43.u...FRW0&type=album",
            "type": "p",
            "width": 200
            }, {
            "height": 227,
            "url": "https://sun9-43.u...NUlg&type=album",
            "type": "q",
            "width": 320
            }, {
            "height": 340,
            "url": "https://sun9-43.u...PyI0&type=album",
            "type": "r",
            "width": 480
            }, {
            "height": 53,
            "url": "https://sun9-43.u...wYLE&type=album",
            "type": "s",
            "width": 75
            }, {
            "height": 340,
            "url": "https://sun9-43.u...PyI0&type=album",
            "type": "x",
            "width": 480
            }],
            "text": "",
            "user_id": 100,
            "has_tags": false
            }
            }, {
            "type": "photo",
            "photo": {
            "album_id": -7,
            "date": 1639823130,
            "id": 457376164,
            "owner_id": -50496202,
            "access_key": "e6236f1f2beabddc2f",
            "sizes": [{
            "height": 80,
            "url": "https://sun9-71.u...JbmQ&type=album",
            "type": "m",
            "width": 130
            }, {
            "height": 87,
            "url": "https://sun9-71.u...KAS4&type=album",
            "type": "o",
            "width": 130
            }, {
            "height": 133,
            "url": "https://sun9-71.u...6Cd4&type=album",
            "type": "p",
            "width": 200
            }, {
            "height": 213,
            "url": "https://sun9-71.u...8KO0&type=album",
            "type": "q",
            "width": 320
            }, {
            "height": 294,
            "url": "https://sun9-71.u...5EMM&type=album",
            "type": "r",
            "width": 478
            }, {
            "height": 46,
            "url": "https://sun9-71.u...2vBI&type=album",
            "type": "s",
            "width": 75
            }, {
            "height": 294,
            "url": "https://sun9-71.u...5EMM&type=album",
            "type": "x",
            "width": 478
            }],
            "text": "",
            "user_id": 100,
            "has_tags": false
            }
            }, {
            "type": "photo",
            "photo": {
            "album_id": -7,
            "date": 1639823130,
            "id": 457376165,
            "owner_id": -50496202,
            "access_key": "57f3d79fcfa63c99f4",
            "sizes": [{
            "height": 94,
            "url": "https://sun9-80.u...fUkA&type=album",
            "type": "m",
            "width": 130
            }, {
            "height": 94,
            "url": "https://sun9-80.u...fUkA&type=album",
            "type": "o",
            "width": 130
            }, {
            "height": 145,
            "url": "https://sun9-80.u...Oujo&type=album",
            "type": "p",
            "width": 200
            }, {
            "height": 232,
            "url": "https://sun9-80.u...bD9k&type=album",
            "type": "q",
            "width": 320
            }, {
            "height": 263,
            "url": "https://sun9-80.u...jGrY&type=album",
            "type": "r",
            "width": 363
            }, {
            "height": 54,
            "url": "https://sun9-80.u...7uS4&type=album",
            "type": "s",
            "width": 75
            }, {
            "height": 263,
            "url": "https://sun9-80.u...jGrY&type=album",
            "type": "x",
            "width": 363
            }],
            "text": "",
            "user_id": 100,
            "has_tags": false
            }
            }, {
            "type": "photo",
            "photo": {
            "album_id": -7,
            "date": 1639823130,
            "id": 457376166,
            "owner_id": -50496202,
            "access_key": "2884440f8ac260bfb2",
            "sizes": [{
            "height": 96,
            "url": "https://sun9-15.u...j_F0&type=album",
            "type": "m",
            "width": 130
            }, {
            "height": 96,
            "url": "https://sun9-15.u...j_F0&type=album",
            "type": "o",
            "width": 130
            }, {
            "height": 148,
            "url": "https://sun9-15.u...2kTM&type=album",
            "type": "p",
            "width": 200
            }, {
            "height": 237,
            "url": "https://sun9-15.u...HXok&type=album",
            "type": "q",
            "width": 320
            }, {
            "height": 356,
            "url": "https://sun9-15.u...LFbA&type=album",
            "type": "r",
            "width": 480
            }, {
            "height": 55,
            "url": "https://sun9-15.u...VQAk&type=album",
            "type": "s",
            "width": 75
            }, {
            "height": 356,
            "url": "https://sun9-15.u...LFbA&type=album",
            "type": "x",
            "width": 480
            }],
            "text": "",
            "user_id": 100,
            "has_tags": false
            }
            }, {
            "type": "photo",
            "photo": {
            "album_id": -7,
            "date": 1639823130,
            "id": 457376167,
            "owner_id": -50496202,
            "access_key": "debe638671eb7dd7ee",
            "sizes": [{
            "height": 97,
            "url": "https://sun9-12.u...SfRA&type=album",
            "type": "m",
            "width": 130
            }, {
            "height": 98,
            "url": "https://sun9-12.u...SfRA&type=album",
            "type": "o",
            "width": 130
            }, {
            "height": 150,
            "url": "https://sun9-12.u...Vkmc&type=album",
            "type": "p",
            "width": 200
            }, {
            "height": 240,
            "url": "https://sun9-12.u...9CJI&type=album",
            "type": "q",
            "width": 320
            }, {
            "height": 383,
            "url": "https://sun9-12.u...5bVA&type=album",
            "type": "r",
            "width": 510
            }, {
            "height": 56,
            "url": "https://sun9-12.u...ZvGg&type=album",
            "type": "s",
            "width": 75
            }, {
            "height": 450,
            "url": "https://sun9-12.u...LX0U&type=album",
            "type": "x",
            "width": 600
            }],
            "text": "",
            "user_id": 100,
            "has_tags": false
            }
            }],
            "post_source": {
            "type": "api"
            },
            "comments": {
            "can_post": 1,
            "count": 0,
            "groups_can_post": true
            },
            "likes": {
            "can_like": 1,
            "count": 8,
            "user_likes": 0,
            "can_publish": 1
            },
            "reposts": {
            "count": 15,
            "user_reposted": 0
            },
            "views": {
            "count": 1006
            },
            "is_favorite": false,
            "donut": {
            "is_donut": false
            },
            "short_text_rate": 0.8,
            "carousel_offset": 0
            }],
            "next_from": "3/-50496202_324404",
            "count": 1000,
            "total_count": 667527
            }
            }
            """;

    private static final String EMPTY_RESPONSE = """
            {
            "response": {
            "items": [],
            "count": 1000,
            "total_count": 31695187
            }
            }
            """;

    private static final String HANDMADE_RESPONSE = """
            {
            "response": {
            "items": [
            {"date": 123},
            {"date": 456},
            {"date": 789}
            ]
            }
            }
            """;

    private static final String ERROR_RESPONSE = """
            {
            "error":
            {"error_code": 5, "error_msg": "User authorization failed: no access_token passed.", "request_params": [
            {"key": "q", "value":"#образование"},
            {"key": "v", "value":"5.131"},
            {"key": "count", "value": "200"},
            {"key": "method", "value": "newsfeed.search"},
            {"key": "oauth", "value":"1"}
            ]
            }
            }
            """;

    @Test
    public void parseRealResponseTest() {
        assertThat(parser.parseResponse(REAL_RESPONSE)).isEqualTo(List.of(
                Instant.ofEpochSecond(1640552018L),
                Instant.ofEpochSecond(1640551826L),
                Instant.ofEpochSecond(1640550300L)
        ));
    }

    @Test
    public void parseEmptyResponseTest() {
        assertThat(parser.parseResponse(EMPTY_RESPONSE)).isEqualTo(Collections.emptyList());
    }

    @Test
    public void parseHandmadeResponseTest() {
        assertThat(parser.parseResponse(HANDMADE_RESPONSE)).isEqualTo(List.of(
                Instant.ofEpochSecond(123L),
                Instant.ofEpochSecond(456L),
                Instant.ofEpochSecond(789L)
        ));
    }

    @Test
    public void parseErrorResponseTest() {
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> parser.parseResponse(ERROR_RESPONSE));
    }
}
