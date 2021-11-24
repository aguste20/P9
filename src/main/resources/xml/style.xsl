<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <script language="javascript">
            function saveChanges(){
            var xr = new XMLHttpRequest();
            var url = "saveNewText.php";
            var text = document.getElementById("mySpan").innerHTML;
            var vars = "newText"+text;

            xr.open("POST", url, true);
            xr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xr.send(vars)
            }

        </script>
        <span id="mySpan" contenteditable="true" onblur="saveChanges()"><?php include("myText.txt"); ?><xsl:text>    </xsl:text><h1>Bohemian rapsody</h1><xsl:text>Is this just fantasy? 
</xsl:text><h1>Caught </h1><xsl:text>in a landsasdasdasdasdide, No escape from reality Open your eyes, Look up to the skies and see, I'm just a poor boy, I need no sympathy, Because I'm easy come, easy go, Little high, little low, Any way the wind blows doesn't really matter to Me, to me Mamaaa, Just killed a man, Put a gun against his head, pulled my trigger, Now he's dead Mamaaa, life had just begun, But now I've gone and thrown it all away Mama, oasdasdasdasdasdasdooh, Didn't mean to make you cry, If I'm not back again this time tomorrow, Carry on, carry on as if nothing really matters Too , my time has come, Sends shivers down my spine, body's aching all The time Goodbye, everybody, I've got to go, Gotta leave you all behind and face the truth Mama, oooh I don't want to die, I sometimes wish I'd never been born at all. I see a little silhouetto of a man, Scaramouch, </xsl:text><h2>Scaramouch</h2><xsl:text>
Tekst tekst
</xsl:text><h2>ny subheader</h2><xsl:text>Will you do the Fandango!
</xsl:text><h1>Hej med dig</h1><xsl:text>
Mere tekst 
</xsl:text><h2>Blabla</h2><xsl:text>


 
 
 
 </xsl:text></span>    </xsl:template>

</xsl:stylesheet>