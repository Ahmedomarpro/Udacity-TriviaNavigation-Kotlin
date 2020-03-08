package com.ao.navigationfragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.ao.navigationfragment.databinding.FragmentGameWonBinding

/**
 * A simple [Fragment] subclass.
 */
class GameWonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentGameWonBinding: FragmentGameWonBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_game_won, container, false)

        fragmentGameWonBinding.nextMatchButton.setOnClickListener { a:View ->
            a.findNavController().navigate(
                GameWonFragmentDirections.actionGameWonFragmentToGameFragment())


         }
                 setHasOptionsMenu(true)
        return fragmentGameWonBinding.root

    }
    private fun getShare() : Intent{
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        //------------
        return ShareCompat.IntentBuilder.from(activity!!)
            .setText(getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
            .setType("text/plain")
            .intent
    }
    private fun shareSuccess() {
        startActivity(getShare())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu, menu)
        // check if the activity resolves
        if (null == getShare().resolveActivity(activity!!.packageManager)) {
            // hide the menu item if it doesn't resolve
            menu?.findItem(R.id.share).setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share -> shareSuccess()
        }
         
        return super.onOptionsItemSelected(item)
    }

}
