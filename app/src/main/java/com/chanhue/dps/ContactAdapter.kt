import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chanhue.dps.R
import com.chanhue.dps.databinding.ItemContactBinding
import com.chanhue.dps.model.Contact

class ContactAdapter(private var contacts: List<Contact>, private val favoriteListener: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            // 연락처 정보를 뷰에 바인딩
            val petProfile = contact.petProfile
            binding.nameTextView.text = petProfile.name
            binding.speciesTextView.text = petProfile.species
            binding.ageTextView.text = petProfile.age.toString()
            Glide.with(binding.thumbnailImageView.context)
                .load(petProfile.thumbnailImage)
                .error(R.drawable.mypage_default_image) // 기본 이미지 로드 실패 시
                .into(binding.thumbnailImageView)

            binding.ivFavorite.setImageResource(
                if (contact.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_empty
            )

            binding.ivFavorite.setOnClickListener {
                favoriteListener(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun updateContacts(newContacts: List<Contact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }

    fun updateContactList(newContacts: List<Contact>) {
        val diffCallback = ContactDiffCallback(contacts, newContacts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        contacts = newContacts
        diffResult.dispatchUpdatesTo(this)
    }
}

class ContactDiffCallback(
    private val oldList: List<Contact>,
    private val newList: List<Contact>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
