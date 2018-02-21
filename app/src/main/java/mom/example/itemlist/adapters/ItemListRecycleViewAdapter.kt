package mom.example.scalablerecyclerviewitem.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import mom.example.scalablerecyclerviewitem.R
import mom.example.scalablerecyclerviewitem.RecourceTypes
import mom.example.scalablerecyclerviewitem.Util
import mom.example.scalablerecyclerviewitem.model.Pokemon
import com.mikhaellopez.circularimageview.CircularImageView

class ItemListRecycleViewAdapter(val context: Context, val items: ArrayList<Pokemon>) :
        RecyclerView.Adapter<ItemListRecycleViewAdapter.ItemViewHolder>() {

    val util = Util()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
        if (items.isEmpty() || holder == null)
            return


        val item = items[position]
        val drawableName = item.imageName.substring(0, item.imageName.lastIndexOf('.'))
        val itemImgId = context.resources.getIdentifier(drawableName,
                RecourceTypes.DRAWABLE.getNameSmall(), context.packageName)
        holder.imageView.setImageResource(itemImgId)
    }

    override fun getItemCount(): Int {
        return items.size
    }



    inner class ItemViewHolder(val view: View ) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<CircularImageView>(R.id.itemPicCI)

        init {

            imageView.setOnClickListener {
                val pivotType = Animation.RELATIVE_TO_SELF
                val scaleAnime = ScaleAnimation(1f,2f,1f,2f,
                        pivotType, 0.5f, pivotType, 0.5f )

                scaleAnime.duration = 400
                scaleAnime.fillAfter = true
                ViewCompat.setElevation(it,1f)
                it.startAnimation(scaleAnime)
            }

        }


    }
}
