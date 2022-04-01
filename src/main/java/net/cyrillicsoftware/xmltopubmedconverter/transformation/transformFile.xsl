<?xml version="1.0" encoding="utf-8" ?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:template match="/">
        <ArticleSet>
            <Article>
                <Journal>
                    <PublisherName>
                            <xsl:value-of select="substring(asset/metadataList/metadata/copyright/statement/valueList/value[@valueType = 'source']/plainText,
                                    7,
                                    string-length(asset/metadataList/metadata/copyright/statement/valueList/value[@valueType = 'source']/plainText))">
                            </xsl:value-of>
                    </PublisherName>
                    <JournalTitle>
                        <xsl:value-of select="asset/metadataList/metadata[@ofType='journal']/titleList/title/valueList/value/plainText">

                        </xsl:value-of>
                    </JournalTitle>
                    <Issn>
                        <xsl:value-of select="asset/metadataList/metadata[@ofType='journal']/externalIdentifierList/externalIdentifier[@ofType='p-issn']/valueList/value/plainText">

                        </xsl:value-of>
                    </Issn>
                    <Volume>
                        <xsl:value-of select="asset/metadataList/metadata[@ofType='issue']/taxonomyIdentifierList/taxonomyIdentifier [@ofType='volume']/valueList/value/plainText">

                        </xsl:value-of>
                    </Volume>
                    <Issue>
                        <xsl:value-of select="asset/metadataList/metadata[@ofType='issue']/taxonomyIdentifierList/taxonomyIdentifier [@ofType='issue-number']/valueList/value/plainText">

                        </xsl:value-of>
                    </Issue>
                    <PubDate>
                        <Year>
                            <xsl:value-of select="asset/metadataList/metadata[@ofType='article']/publicationHistoryList/publicationHistory/publicationDate/year/valueList/value/plainText">

                            </xsl:value-of>
                        </Year>
                        <Month>
                            <xsl:value-of select="asset/metadataList/metadata[@ofType='article']/publicationHistoryList/publicationHistory/publicationDate/month/valueList/value/plainText">

                            </xsl:value-of>
                        </Month>
                        <Day>
                            <xsl:value-of select="asset/metadataList/metadata[@ofType='article']/publicationHistoryList/publicationHistory/publicationDate/day/valueList/value/plainText">

                            </xsl:value-of>
                        </Day>
                    </PubDate>
                </Journal>
                <ArticleTitle>
                    <xsl:value-of select="asset/metadataList/metadata[@ofType='article']/titleList/title/valueList/value/plainText">

                    </xsl:value-of>
                </ArticleTitle>
                <FirstPage>
                    <xsl:value-of select="asset/metadataList/metadata[@ofType='article']/pagination/pageRangeList/pageRange/firstPage/valueList/value/plainText">

                    </xsl:value-of>
                </FirstPage>
                <LastPage>
                    <xsl:value-of select="asset/metadataList/metadata[@ofType='article']/pagination/pageRangeList/pageRange/lastPage/valueList/value/plainText">

                    </xsl:value-of>
                </LastPage>
                <Language>
                    <xsl:value-of select="substring(asset/resourceList/resource[@ofType='languages']/languageList/language/textFormatList/textFormat/plainText,0,4)">

                    </xsl:value-of>
                </Language>
                <ArticleIdList>
                    <ArticleId IdType="doi">
                        <xsl:value-of select="asset/metadataList/metadata[@ofType='article']/externalIdentifierList/externalIdentifier[@ofType='doi']/valueList/value/plainText">

                        </xsl:value-of>
                    </ArticleId>
                    <ArticleId IdType="pii">
                        <xsl:value-of select="asset/metadataList/metadata[@ofType='article']/externalIdentifierList/externalIdentifier[@ofType='accession-number']/valueList/value/plainText">

                        </xsl:value-of>
                    </ArticleId>
                </ArticleIdList>
                <CopyrightInformation>
                    <xsl:value-of select="asset/metadataList/metadata[@ofType='article']/copyright[@ofType='single']/statement/valueList/value[@valueType='normalized']/plainText"></xsl:value-of>
                </CopyrightInformation>
            </Article>
        </ArticleSet>
    </xsl:template>
</xsl:stylesheet>