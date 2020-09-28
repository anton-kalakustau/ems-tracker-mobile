package com.example.firstapp

import android.nfc.FormatException
import android.os.AsyncTask
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.Reader
import java.io.StringReader
import java.net.URL
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class DataFetcher: AsyncTask<String, Void,  Array<Array<String>>>() {
    private var exception: Exception? = null

    override fun doInBackground(vararg emsId: String): Array<Array<String>> {
        val url = URL("https://items.ems.post/api/publicTracking/track?language=EN&itemId=" + emsId[0])
        val rawResult = url.readText()
        return formatOutput(rawResult)
    }

    @Throws(FormatException::class)
    fun formatOutput(output: String): Array<Array<String>> {
        val regex = Regex("<table>.*<\\/table>", RegexOption.DOT_MATCHES_ALL)
        val tableOutput = regex.find(output)?.groupValues?.first()
            ?: throw FormatException("Received unexpected response format")

        val xFactory = XPathFactory.newInstance();
        val xPath = xFactory.newXPath()
        val headerResults = xPath.evaluate("//thead/tr/th", InputSource(StringReader(tableOutput)), XPathConstants.NODESET) as NodeList

        val headers: MutableList<String> = ArrayList()
        val data: MutableList<Array<String>> = ArrayList()
        val maxLengths: MutableList<Int> = ArrayList()

        for (i in 0..headerResults.length-1) {
            headers.add(headerResults.item(i).textContent)
            maxLengths.add(headerResults.item(i).textContent.length)
        }

        val dataResults = xPath.evaluate("//tbody/tr", InputSource(StringReader(tableOutput)), XPathConstants.NODESET) as NodeList
        for (i in 0..dataResults.length - 1) {
            val dataRow: MutableList<String> = ArrayList()
            val xElement = dataResults.item(i)
            for (j in 0..xElement.childNodes.length-1) {
                val child = xElement.childNodes.item(j)
                if (child.nodeName != "td") {
                    continue
                }
                dataRow.add(child.textContent)
                maxLengths[dataRow.size - 1] = Math.max(maxLengths[dataRow.size - 1], child.textContent.length)
            }

            data.add(dataRow.toTypedArray())
        }


        return data.toTypedArray()
    }
}