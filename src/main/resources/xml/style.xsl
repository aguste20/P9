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
<script language="javascript">
    borto = {
    tmpEl: document.createElement('div'),
    /* create element like in jquery with string &lt;tag attribute1 attribute2 /> or &lt;tag attribute1>&lt;/tag> */
    htmlToDom: function(htmlEl){
    borto.tmpEl.innerHTML = htmlEl;
    return borto.tmpEl.children[0]
    },
    wrapSelection: function(htmlEl){
    var sel = window.getSelection();
    // In firefox we can select multiple area, so they are multiple range
    for(var i = sel.rangeCount;i--;){
    var wrapper = borto.htmlToDom(htmlEl)
    var range = sel.getRangeAt(i);
    wrapper.appendChild(range.extractContents());
    range.insertNode(wrapper);
    }
    },
    command: (name,argument)=>{
    switch(name){
    case "createLink":
    argument = prompt("Quelle est l'adresse du lien ?");
    break;case 'heading' :
    borto.wrapSelection('&lt;'+argument+'/>')
    return;
    }
    if(typeof argument === 'undefined') {
    argument = '';
    }
    document.execCommand(name, false, argument);
    }
    }
</script>

<button onclick="borto.command('bold')">Bold</button>
<button onclick="borto.command('italic')"><i>I</i></button>
<button onclick="borto.command('underline')"><u>U</u></button>
<button onclick="borto.command('createLink')"><u>createLink</u></button>
<select onchange="borto.command('heading', this.value); this.selectedIndex = 0;">
<option value="">Title</option>
<option value="h1">Title 1</option>
<option value="h2">Title 2</option>
<option value="h3">Title 3</option>
<option value="h4">Title 4</option>
<option value="h5">Title 5</option>
<option value="h6">Title 6</option>
</select>

<span id="mySpan" contenteditable="true" onblur="saveChanges()"><?php include("myText.txt"); ?><xsl:text>   </xsl:text><h1>Bohemian rapsody</h1><xsl:text>Is this just fasy? 
</xsl:text><h1>Caught </h1><xsl:text>in a landside, No escape fr</xsl:text><h1>om re</h1><xsl:text>ality Open your eyes, Look up to the skies and see, I'm just a poor boy, I need no sympathy, Because I'm easy come, easy go, Little high, little low, Any way the wind blows doesn't really matter to Me, to me Mamaaa, Just killed a man, Put a gun against his head, pulled my trigger, Now he's dead Mamaaa, life had just begun, But now I've gone and thrown it all away Mama, oooh, Didn't mean to make you cry, If I'm not back again this time tomorrow, Carry on, carry on as if nothing really matters Too , my time has come, Sends shivers down my spine, body's aching all The time Goodbye, everybody, I've got to go, Gotta leave you all behind and face the truth Mama, oooh I don't want to die, I sometimes wish I'd never been born at all. I see a little silhouetto of a man, Scaramouch, </xsl:text><h2>Scaramouch</h2><xsl:text>
Tekst tekst 
</xsl:text><h2>ny subheader</h2><xsl:text>Will you do the Fandango!
</xsl:text><h1>Hej med dig</h1><xsl:text>
Mere tekst 
</xsl:text><h2>Blabla</h2><xsl:text>


 
 
 </xsl:text></span>    </xsl:template>

</xsl:stylesheet>