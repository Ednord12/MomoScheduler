package com.example.ea_create.momosheduler

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.widget.TextView
import android.widget.Toast
import com.example.ea_create.momosheduler.Adapter.Operations_Adapter
import com.example.ea_create.momosheduler.Models.Operation
import com.example.ea_create.momosheduler.Network.RetrofitInit
import com.example.ea_create.momosheduler.Network.RetrofitService
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_operation_contener.*
import kotlinx.android.synthetic.main.activity_statistiques.*
import retrofit2.Call
import retrofit2.Response
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Statistiques : AppCompatActivity() {

    private val STORAGE_CODE: Int = 100;
    var operation: ArrayList<Operation> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistiques)



        ltv_statistiques.setOnItemClickListener { parent, view, position, id ->


            OpenDialog(operation[position])

        }

        btn_stat_Ok.setOnClickListener { l -> getDate() }


        /***********************************/

        btn_stat_Ok_pdf.setOnClickListener {
            //we need to handle runtime permission for devices with marshmallow and above
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                //system OS >= Marshmallow(6.0), check permission is enabled or not
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    //permission was not granted, request it
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, STORAGE_CODE)
                }
                else{
                    //permission already granted, call savePdf() method
                    savePdf()
                }
            }
            else{
                //system OS < marshmallow, call savePdf() method
                savePdf()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        //MakeRequest()
    }

    fun getDate() {
        var startd = txt_stat_startdate.text.trim().toString()
        var endd = txt_stat_enddate.text.trim().toString()
        if (startd.contains('-') and endd.contains('-')) {
            MakeRequest(startd, endd)
        } else {
            Snackbar.make(relativeLayout,"La période entrée ne respect pas le format, rééssayez",Snackbar.LENGTH_LONG)

        }
    }

    private fun OpenDialog(operation: Operation) {

        var dialog: Dialog = Dialog(this)

        dialog.setContentView(R.layout.operation_details)


        var dg_client = dialog.findViewById<TextView>(R.id.txt_dg_client)
        var dg_solde = dialog.findViewById<TextView>(R.id.txt_dg_mintant)
        var dg_operation = dialog.findViewById<TextView>(R.id.txt_dg_operation)
        var dg_operator = dialog.findViewById<TextView>(R.id.txt_dg_opertor)
        var dg_tel = dialog.findViewById<TextView>(R.id.txt_dg_tel)
        var dg_tr_ref = dialog.findViewById<TextView>(R.id.txt_dg_tr_reference)
        var dg_id_ref = dialog.findViewById<TextView>(R.id.txt_dg_id_reference)


        dg_id_ref.text = "ID Pièce: ${operation.customerIDcard}"
        dg_client.text = "Client: ${operation.customer}"
        dg_operation.text = "Opération: ${operation.actionMode}"
        dg_solde.text = "Montant: ${operation.solde}"
        dg_tel.text = "Téléphone: ${operation.phone}"
        dg_operator.text = "Operateur: ${operation.operator}"
        dg_tr_ref.text = "Tr Réference: ${operation.referencecode}"

        dialog.show()


    }

    private fun MakeRequest(st_d: String, end_d: String) {

        val retro = RetrofitInit.retrofit().create(RetrofitService::class.java)
        var r = retro.getOperationbyOperatorBetweenDate(Global.operator, st_d, end_d)
        r.enqueue(object : retrofit2.Callback<ArrayList<Operation>> {

            override fun onFailure(call: Call<ArrayList<Operation>>, t: Throwable) {

                showDialog(
                    "Nous rencontrons des difficultés à nous connecté au serveur.\n Verifiez votre " +
                            "connexion "
                )
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<Operation>>, response: Response<ArrayList<Operation>>) {


                response.let {


                    if (response.isSuccessful) {
                        operation = response.body()!!


                        var adapter = Operations_Adapter(applicationContext, operation)
                        ltv_statistiques.adapter = adapter

                    }
                }

            }
        })

    }

    /*********************************************/




    private fun savePdf() {
        //create object of Document class
        val mDoc = Document()
        //pdf file name
        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        //pdf file path
        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName +".pdf"
        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            //open the document for writing
            mDoc.open()
            var mText = "\r\rRecette du jour\n"
            //get text from EditText i.e. textEt
            for (txt:Operation in operation){

                mText+="\n ${txt.toString()}"
            }


            //add author of the document (metadata)
            mDoc.addAuthor("Ednord")

            //add paragraph to the document
            mDoc.add(Paragraph(mText))

            //close document
            mDoc.close()

            //show file saved message with file name and path
            Toast.makeText(this, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception){
            //if anything goes wrong causing exception, get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted, call savePdf() method
                    savePdf()
                }
                else{
                    //permission from popup was denied, show error message
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    /********************************************/

    fun showDialog(text: String) {

        AlertDialog.Builder(this)
            .setMessage(text).show()
    }
}



