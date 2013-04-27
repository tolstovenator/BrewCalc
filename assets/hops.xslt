<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <hops>
    
	
      <xsl:for-each select="Hops/Hops">
        <hop>
          <name><xsl:value-of select="F_H_NAME"/></name>
          <origin><xsl:value-of select="F_H_ORIGIN"/></origin>
          <alpha><xsl:value-of select="F_H_ALPHA"/></alpha>
          <beta><xsl:value-of select="F_H_BETA"/></beta>
          <type><xsl:value-of select="F_H_TYPE"/></type>
          <notes><xsl:value-of select="F_H_NOTES"/></notes>
          <hsi><xsl:value-of select="F_H_HSI"/></hsi>
          <humulene>0</humulene>
          <cohumulone>0</cohumulone>
          <myrcene>0</myrcene>
          <caryophyllene>0</caryophyllene>
        </hop>
      </xsl:for-each>
    </hops>
</xsl:template>

</xsl:stylesheet>