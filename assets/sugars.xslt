<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <sugars>
    
	
      <xsl:for-each select="Grains/Grain">
        <sugar>
          <name><xsl:value-of select="F_G_NAME"/></name>
          <origin><xsl:value-of select="F_G_ORIGIN"/></origin>
          <supplier><xsl:value-of select="F_G_SUPPLIER"/></supplier>
          <description><xsl:value-of select="F_G_NOTES"/></description>
          <sugarType><xsl:value-of select="F_G_TYPE"/></sugarType>
          <mustMash><xsl:value-of select="F_G_RECOMMEND_MASH"/></mustMash>
          <colour><xsl:value-of select="F_G_COLOR"/></colour>
          <fgDry><xsl:value-of select="F_G_YIELD"/></fgDry>
          <moisture><xsl:value-of select="F_G_MOISTURE"/></moisture>
          <coarseFineDiff><xsl:value-of select="F_G_COARSE_FINE_DIFF"/></coarseFineDiff>
          <maxInBatch><xsl:value-of select="F_G_MAX_IN_BATCH"/></maxInBatch>
          <protein><xsl:value-of select="F_G_PROTEIN"/></protein>
          <diastaticPower><xsl:value-of select="F_G_DIASTATIC_POWER"/></diastaticPower>
          <tsn>0</tsn>
          <hop><xsl:value-of select="F_G_IBU_GAL_PER_LB"/></hop>
        </sugar>
      </xsl:for-each>
    </sugars>
</xsl:template>

</xsl:stylesheet>