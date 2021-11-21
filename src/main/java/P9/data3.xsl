<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>


    <xsl:template match="/">

        <xsl:for-each select="h1">
            <h1><xsl:value-of select="h2" /></h1>
            <xsl:text> This is </xsl:text><xsl:value-of select="name"/><xsl:text>. You can search by serial number, product/specification number or product designation.
            However, searching by serial or product/specification number will supply the most in-depth
            information, but only works for products manufactured after 1991. Please observe that the
            information available varies depending on the product and year of production. Information
            for older products is limited.
            </xsl:text>
        </xsl:for-each>

        <p>blablabla</p>



    </xsl:template>

</xsl:stylesheet>
