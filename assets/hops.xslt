<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <hops>
    
	
      <xsl:for-each select="Hops/Hops">
        <hop>
          <name><xsl:value-of select="F_H_NAME"/></name>
          <alpha><xsl:value-of select="F_H_ALPHA"/></alpha>
        </hop>
      </xsl:for-each>
    </hops>
</xsl:template>

</xsl:stylesheet>