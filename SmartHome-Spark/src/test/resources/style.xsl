<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output indent="no" omit-xml-declaration="yes" />
	<xsl:template match="@*|*|processing-instruction()|comment()">
		<xsl:apply-templates select="*|@*|text()|processing-instruction()|comment()" />
	</xsl:template>
	<xsl:template match="text()"><xsl:value-of select="."/></xsl:template>
</xsl:stylesheet>