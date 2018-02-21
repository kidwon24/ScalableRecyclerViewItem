package mom.example.scalablerecyclerviewitem

import android.app.Activity
import android.util.Xml
import mom.example.scalablerecyclerviewitem.enums.PokemonsType
import mom.example.scalablerecyclerviewitem.model.Pokemon
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class AppHelper {

    companion object {
        val util = Util()
        private val ns = null

        fun parsePokemonList(activity: Activity) : ArrayList<Pokemon> {
            val parser = Xml.newPullParser()
            val pokemons = ArrayList<Pokemon>()
            val resources = activity.resources
            val stream: InputStream = resources.assets.open("items.xml")
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(stream, Xml.Encoding.UTF_8.toString())
            parser.nextTag()
            var tagName = ""

            fun readPokemon() : Pokemon {
                var name = ""
                var type = ""
                var img = ""
                parser.require(XmlPullParser.START_TAG, ns, "pokemon")
                val id = parser.getAttributeValue(null, "id").toLong(10)

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.eventType != XmlPullParser.START_TAG) {
                        continue;
                    }
                    when(parser.name) {
                        "name" -> name = readTagInnerText(parser, "name")
                        "type"  -> type  = readTagInnerText(parser, "type")
                        "imageName" -> img       = readTagInnerText(parser, "imageName")
                    }
                }

                val pokemon = Pokemon(id, name, PokemonsType.valueOf(type.toUpperCase()), img)

                return pokemon
            }

            try {
                parser.require(XmlPullParser.START_TAG, ns, "pokemons")
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.eventType != XmlPullParser.START_TAG) {
                        continue
                    }
                    tagName = parser.name
                    if (tagName.equals("pokemon")) {
                        pokemons.add(readPokemon())
                    }
                }
            } catch (ex: Exception) {
                println("Something went wrong: $ex")
                throw ex
            } finally {
                stream.close()
            }


            return  pokemons
        }

        @Throws(IOException::class, XmlPullParserException::class)
        fun readTagInnerText(parser: XmlPullParser, tagName: String): String {
            var result = ""
            parser.require(XmlPullParser.START_TAG, ns, tagName)
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.text
                parser.nextTag()
            }
            return result
        }
    }
}