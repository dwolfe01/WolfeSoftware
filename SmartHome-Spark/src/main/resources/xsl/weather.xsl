<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output indent="yes" omit-xml-declaration="yes" method="html" />

	<xsl:template match="/">
		<xsl:apply-templates select="//item" />
	</xsl:template>

	<xsl:template match="item">
		<div class="weather">
			<xsl:value-of select="title" />
			<span class="description"><xsl:value-of select="description" /></span>
		</div>
	</xsl:template>

</xsl:stylesheet>