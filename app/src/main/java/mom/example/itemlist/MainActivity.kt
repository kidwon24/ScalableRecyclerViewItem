package mom.example.scalablerecyclerviewitem

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import mom.example.scalablerecyclerviewitem.fragments.ItemsListFragment
import mom.example.scalablerecyclerviewitem.model.Pokemon

class MainActivity : AppCompatActivity() {

    val items = ArrayList<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.items.addAll(AppHelper.parsePokemonList(this))

        val itemsListFragment = ItemsListFragment()
        itemsListFragment.load(anActivity = this)
    }
}
