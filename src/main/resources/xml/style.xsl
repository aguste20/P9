<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>


    <xsl:template match="/">

            <h1>Overskrift 1</h1>
            <xsl:text> This is </xsl:text><xsl:value-of select="eObject/name"/><xsl:text>. You can search by serial number, product/specification number or product designation.
            However, searching by</xsl:text> <b> serial</b> <xsl:text> or product/specification number will supply the most in-depth
            information, but only works for products manufactured after 1991. Please observe that the
            information available varies depending on the product and year of production. Information
            for older products is limited.
            </xsl:text> <p>blablabla</p>

    </xsl:template>

</xsl:stylesheet>
