package ca.qc.cgodin.slotmachinetp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cgodin.slotmachinetp.databinding.FragmentMachineBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters


    private lateinit var binding: FragmentMachineBinding // Binding

    private var actifs = 100; // Actifs de départ

    private val code = "hello" //Code secret pour gagner 100$

   var modeJeu = ""
    var mise = 0 //Valeur par défaut

    // Images de départ
    var img1 = R.drawable.bird
    var img2 = R.drawable.bird
    var img3 = R.drawable.bird

    private var gainOuPerte = ""


    // Fonction pour mettre à jour le bouton de jouer
    fun updateBtn() {
        if (binding.btnOneDollar.isChecked && actifs > 0 || binding.btnTwoDollars.isChecked && actifs > 1 || binding.btnFiveDollars.isChecked && actifs > 4) {
            binding.button.isEnabled = true
        }
    }

    // Fonction pour mettre à jour le bouton de mise de 1$
    fun btnOneActif() {
        binding.btnOneDollar.isEnabled = actifs > 0

    }
    // Fonction pour mettre à jour le bouton de mise de 2$
    fun btnTwoActif() {
        binding.btnTwoDollars.isEnabled = actifs > 1
    }
    // Fonction pour mettre à jour le bouton de mise de 5$
    fun btnFiveActif() {
        binding.btnFiveDollars.isEnabled = actifs > 4
    }

    //Instance de partie viewModel pour ajouter les infos sur la BD
    private val partieViewModel: PartieViewModel by lazy {
        ViewModelProvider(
            this,
            PartieViewModelFactory(requireActivity().application)
        ).get(PartieViewModel::class.java)
    }


    //  fonction qui Rend le button de jouer actif dépendamment si l'utilisateur a assez d'argent
    private fun btnEstActif() {
        binding.button.isEnabled =
            binding.btnFiveDollars.isChecked || binding.btnTwoDollars.isChecked || binding.btnOneDollar.isChecked

    }

//    Fonction qui dit si le casse cou est coché
    fun casseCou(): Boolean {
        return binding.checkBox.isChecked
    }


    //Fonciton qui dit combien de logos GG il y a dans la partie actuelle
    fun nombreGG(img1: Int, img2: Int, img3: Int): Int {
        var count = 0
        val logo = R.drawable.logocgg
        if (img1 == logo) {
            count++
        }
        if (img2 == logo) {
            count++
        }
        if (img3 == logo) {
            count++
        }
        return count
    }

    //Rétourne une image au hasard
    private fun imageRandom(): Int {
        val chiffre = Random()

        val images = listOf(
            R.drawable.bird,
            R.drawable.finance,
            R.drawable.diamond,
            R.drawable.dollar,
            R.drawable.dragon,
            R.drawable.fish,
            R.drawable.king,
            R.drawable.snow,
            R.drawable.mango,
            R.drawable.logocgg
        )

        return images[chiffre.nextInt(images.size)]
    }


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMachineBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var color = ""//Variable pour la couleur du background initialisée a une chaine vide
        val gainString = getString(R.string.gainRes) // Prend la valeeur du string gain, peut être écrit dans la base de données si le joueur gagne
        val perteString = getString(R.string.perteRes) // Prend la valeur du string de perte, peut être écrit dans la base de données si le joueur perd
        val miseRes = getString(R.string.miseRes) //
        var soldeRes = getString(R.string.nouv_soldeRes)
        val messageGagne = getString(R.string.wisToast) //Message qui va être affichée si le joueur gagne
        var miseString = "" //Variable qui va récevoir la mise en string

        var prixString = "" //Variable qui va recevoir le prix(argent remporté) en string
        var soldeString = "" //Variable qui va recevoir le solde en string


        binding.actifCurrent.setText("$actifs $") // Met les actifs dans le textField pour id actifCurrent


        // Ajouter 100 dollars si le code secret est bon
        binding.editTextTextPassword.doAfterTextChanged {
            if (binding.editTextTextPassword.text.toString() == code) {
                actifs = actifs + 100
                binding.actifCurrent.setText(" $actifs $")
                Snackbar.make(view, getString(R.string.win_message), Snackbar.LENGTH_LONG).show()
                binding.editTextTextPassword.setText("")
                binding.btnOneDollar.isEnabled = true
                binding.btnFiveDollars.isEnabled = true
                binding.btnTwoDollars.isEnabled = true
                binding.button.isEnabled = true

            }
        }


        //Bouton de 5 dollars n'est pas activé par défaut, la fonction btnFiveActif() va décider si l'utilisateur a assez d'argent pour activer le bouton
        binding.btnFiveDollars.isEnabled = false
        btnFiveActif()
        binding.btnFiveDollars.setOnClickListener {//Met la mise à 5 et active le bouton jouer si le bouton est actif
            mise = 5
            binding.button.isEnabled = true

        }

        //Bouton de 2 dollars à le même fonctionnement que le bouton de 5 dollars, adapté à deux dollars
        binding.btnTwoDollars.isEnabled = false
        btnTwoActif()
        binding.btnTwoDollars.setOnClickListener {
            mise = 2
            binding.button.isEnabled = true
        }

        //Bouton de 1 dollar a le même focntionnement aussi
        binding.btnOneDollar.isEnabled = false
        btnOneActif()
        binding.btnOneDollar.setOnClickListener {
            mise = 1
            binding.button.isEnabled = true

        }


        btnEstActif() // Vérifie les conditions pour que le bouton jouer soit actif

        binding.button.setOnClickListener {// Listener d'action sur bouton jouer

            //Appele la fonction imageRandom pour mêttre des inmages aléatoires
            img1 = imageRandom()
            img2 = imageRandom()
            img3 = imageRandom()
            var prix = 0
            //Assigner les images aux ImageViews slot1, slot2, slot3
            binding.slot1.setImageResource(img1)
            binding.slot2.setImageResource(img2)
            binding.slot3.setImageResource(img3)




            if (casseCou()) {
                if (nombreGG(img1, img2, img3) == 3
                ) { // Action à prendre si le mode casse cou est activé et les 3 images sont le logo gg, la fonction nombreGG retourne le nombre de logos GG


                    prix = mise * 100 // l'utilisateur gagne 100 fois sa mise
                    color = "#00FFFF" //La couleur est verte
                    gainOuPerte = "gain"
                    actifs = actifs + prix // Le prix est ajouté aux actifs

                    //Toast affichant le montant remporté
                    Toast.makeText(
                        requireContext(),
                        "$messageGagne ${prix} $",
                        Toast.LENGTH_SHORT
                    ).show();


                    //variables qui vont être passées à la base de donnees
                    miseString = "${miseRes}${mise}$"
                    prixString = "${prix}$"
//                    prixString = "${gainString}${prix}$"
                    soldeString = "${soldeRes}${actifs}$"
                } else if (nombreGG(img1, img2, img3) == 2) {
                    // Action à prendre si le mode casse cou n'est pas activé
                    prix = mise * 10 // prix est égal à 25 fois la mise de l'utilisateur
                    color = "#00FFFF" // couleur est verte aussi
                    gainOuPerte = "gain"
                    actifs = actifs + prix // prix est ajouté aux actifs

                    //Toast indiquant le montant remporté
                    Toast.makeText(
                        requireContext(),
                        "${messageGagne} ${prix} $",
                        Toast.LENGTH_SHORT
                    ).show();

                    //Varibles pour la base de données
                    miseString = "${miseRes}${mise}$"
                    prixString = "${prix}$"
//                    prixString = "${gainString}${prix}$"
                    soldeString = "${soldeRes}${actifs}$"
                } else {
                    prix = 0 - mise  //le prix équivaut à la mise négative
                    color = "#FA8072" //Couleur est rouge
                    gainOuPerte = "perte"
                    actifs = actifs - mise // la mise est déduite des actifs

                    //Variables qui serront passées à la base de données
                    miseString = "${miseRes}${mise}$"
                    prixString = "${prix}$"
//                    prixString = "${perteString}${prix}$"
                    soldeString = "${soldeRes}${actifs}$"
                }

            }
            //Mode normal
            else if (!casseCou()) { //Action si le mode casse cou est n'est pas activé
                if (img1 == img2 && img2 == img3) {
                    prix = mise * 25 // le prix est égal à la mise fois 10


                    color = "#00FFFF" // couleur verte est assignée
                    gainOuPerte = "gain"
                    actifs = actifs + prix // prix est ajouté aux actifs

                    //Toast affiche le montant gagnant
                    Toast.makeText(
                        requireContext(),
                        "${messageGagne} ${prix} $",
                        Toast.LENGTH_SHORT
                    ).show();

                    //Variables pour la base de données
                    miseString = "${miseRes}${mise}$"
                    prixString = "${prix}$"
//                    prixString = "${gainString}${prix}$"
                    soldeString = "${soldeRes}${actifs}$"


                } else if (img1 == img2 || img1 == img3 || img2 == img3) {
                    //Si le mode casse cou n'est pas activé et que le joueur gagne quand même deux images identiques, il recoit 1 fois sa mise
                    prix = mise

                    color = "#00FFFF"
                    actifs = actifs + prix
                    gainOuPerte = "gain"

                    Toast.makeText(
                        requireContext(),
                        "${messageGagne} ${prix} $",
                        Toast.LENGTH_SHORT
                    ).show();

                    miseString = "${miseRes}${mise}$"
//                    prixString = "${gainString}${prix}$"
                    prixString = "${prix}$"
                    soldeString = "${soldeRes} ${actifs}$"
                } else { //Action si le joueur perd

                    prix = 0 - mise  //le prix équivaut à la mise négative
                    color = "#FA8072" //Couleur est rouge
                    gainOuPerte = "perte"
                    actifs = actifs - mise // la mise est déduite des actifs

                    //Variables qui serront passées à la base de données
                    miseString = "${miseRes}${mise}$"
//                    prixString = "${perteString}${prix}$"
                    prixString = "${prix}$"
                    soldeString = "${soldeRes}${actifs}$"

                }
            }

                //passage du mode de jeu qui ser
            modeJeu = if (casseCou()) {
                "cassecou"
            } else {
                "normal"
            }


            //On crée un objet partie avec les attributs suivants
            val partie = Partie(0, img1, img2, img3, miseString, prixString, soldeString, color, modeJeu) //Id est 0 pour que room les assigne automatiquement

            partieViewModel.insert(partie) // ViewModel insère la partie dans la base de données


            binding.actifCurrent.setText("$actifs $") //On met à jour les actifs


            //Rend le bouton de jouer actif ou pas selon l'argent qui reste à l'utilisateur
            if (actifs < 1 && binding.btnOneDollar.isChecked) {
                binding.button.isEnabled = false
            }
            if (actifs < 2 && binding.btnTwoDollars.isChecked) {
                binding.button.isEnabled = false
            }
            if (actifs < 5 && binding.btnFiveDollars.isChecked) {
                binding.button.isEnabled = false
            }

            //Désactiver les boutons de mise selon les actifs
            btnOneActif()
            btnTwoActif()
            btnFiveActif()


        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        Enregistrer l'état actuel de la partie
        outState.putInt("game_mise", mise)
        outState.putInt("game_assets", actifs)
        outState.putInt("img1", img1)
        outState.putInt("img2", img2)
        outState.putInt("img3", img3)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

//        prendre les données sauvegardées et les remettre dans la vue
//        L'affectation des boutons  n'est pas faite explicitement parce qu'elle se fait déjà automatiquement. Par exemple le casse-cou gardera son état quand on passe en mode paysage
        if (savedInstanceState != null) {
            actifs = savedInstanceState.getInt("game_assets")
            binding.actifCurrent.text = actifs.toString()
            mise = savedInstanceState.getInt("game_mise")
            if (mise == 5) {
                binding.btnFiveDollars.isChecked = true
            } else if (mise == 2) {
                binding.btnTwoDollars.isChecked = true
            } else if (mise == 5) {
                binding.btnFiveDollars.isChecked = true
            }
            img1 = savedInstanceState.getInt("img1")
            binding.slot1.setImageResource(img1)
            img2 = savedInstanceState.getInt("img2")
            binding.slot2.setImageResource(img2)
            img3 = savedInstanceState.getInt("img3")
            binding.slot3.setImageResource(img3)



            btnOneActif()
            btnTwoActif()
            btnFiveActif()

            updateBtn()
        }

    }


    //??
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MachineFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}