<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <waters>
    
	
      <xsl:for-each select="Waters/Water">
        <water>
          <name><xsl:value-of select="F_W_NAME"/></name>
          <bestBeer><xsl:value-of select="F_W_NOTES"/></bestBeer>
          <bicarbonade><xsl:value-of select="F_W_BICARB"/></bicarbonade>
          <calcium><xsl:value-of select="F_W_CALCIUM"/></calcium>
          <chloride><xsl:value-of select="F_W_CHLORIDE"/></chloride>
          <magnesium><xsl:value-of select="F_W_MAGNESIUM"/></magnesium>
          <ph><xsl:value-of select="F_W_PH"/></ph>
          <sodium><xsl:value-of select="F_W_SODIUM"/></sodium>
          <sulfate><xsl:value-of select="F_W_SULFATE"/></sulfate>
        </water>
      </xsl:for-each>
    </waters>
</xsl:template>

</xsl:stylesheet>