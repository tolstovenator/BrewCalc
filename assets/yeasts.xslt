<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <yeasts>
    
	
      <xsl:for-each select="Yeast/Yeast">
        <yeast>
          <name><xsl:value-of select="F_Y_NAME"/></name>
          <lab><xsl:value-of select="F_Y_LAB"/></lab>
          <catalogId><xsl:value-of select="F_Y_PRODUCT_ID"/></catalogId>
          <type><xsl:value-of select="F_Y_TYPE"/></type>
          <form><xsl:value-of select="F_Y_FORM"/></form>
          <flocculation><xsl:value-of select="F_Y_FLOCCULATION"/></flocculation>
          <minAttenuation><xsl:value-of select="F_Y_MIN_ATTENUATION"/></minAttenuation>
          <maxAttenuation><xsl:value-of select="F_Y_MAX_ATTENUATION"/></maxAttenuation>
          <minTemp><xsl:value-of select="F_Y_MIN_TEMP"/></minTemp>
          <maxTemp><xsl:value-of select="F_Y_MAX_TEMP"/></maxTemp>
          <useStarter><xsl:value-of select="F_Y_USE_STARTER"/></useStarter>
          <addToSecondary><xsl:value-of select="F_Y_ADD_TO_SECONDARY"/></addToSecondary>
          <maxReuse><xsl:value-of select="F_Y_MAX_REUSE"/></maxReuse>
          <flavour><xsl:value-of select="F_Y_NOTES"/></flavour>
          <notes><xsl:value-of select="F_Y_BEST_FOR"/></notes>
        </yeast>
      </xsl:for-each>
    </yeasts>
</xsl:template>

</xsl:stylesheet>