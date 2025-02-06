package dev.langchain4j.data.message;

import dev.langchain4j.data.pdf.PdfFile;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class PdfFileContentTest {
    @Test
    void test_methods() {
        PdfFile urlPdfFile = PdfFile.builder()
            .url(URI.create("https://example.com/pdfFile.pdf"))
            .build();
        PdfFileContent pdfFileContent = new PdfFileContent(urlPdfFile);

        assertThat(pdfFileContent.pdfFile()).isEqualTo(urlPdfFile);
        assertThat(pdfFileContent.type()).isEqualTo(ContentType.PDF);

        assertThat(pdfFileContent)
            .hasToString(
                "PdfFileContent { " +
                    "pdfFile = PdfFile { " +
                    "url = \"https://example.com/pdfFile.pdf\", " +
                    "base64Data = null } " +
                    "}");
    }

    @Test
    public void test_equals_hashCode() {
        PdfFileContent pdf1 = PdfFileContent.from("https://example.com/pdfFile1.pdf");
        PdfFileContent pdf2 = PdfFileContent.from("https://example.com/pdfFile1.pdf");

        PdfFileContent pdf3 = PdfFileContent.from("https://example.com/pdfFile2.pdf");
        PdfFileContent pdf4 = PdfFileContent.from("https://example.com/pdfFile2.pdf");

        assertThat(pdf1)
            .isEqualTo(pdf1)
            .isNotEqualTo(null)
            .isNotEqualTo(new Object())
            .isEqualTo(pdf2)
            .hasSameHashCodeAs(pdf2)
            .isNotEqualTo(pdf3)
            .isNotEqualTo(pdf4);

        assertThat(pdf3)
            .isEqualTo(pdf3)
            .isEqualTo(pdf4)
            .hasSameHashCodeAs(pdf4);
    }

    @Test
    void should_replace_all_space_from_url() {
        PdfFileContent content = PdfFileContent.from("https://example.com/ pdf File .pdf");
        assertThat(content.pdfFile().url().toString()).isEqualTo("https://example.com/%20pdf%20File%20.pdf");
    }

    @Test
    void should_not_do_anything_with_encoded_or_no_spaced_Urls() {
        PdfFileContent contentEncoded = PdfFileContent.from("https://example.com/%20pdf%20File%20.pdf");
        PdfFileContent content = PdfFileContent.from("https://example.com/pdfFile.pdf");

        assertThat(contentEncoded.pdfFile().url().toString()).isEqualTo("https://example.com/%20pdf%20File%20.pdf");
        assertThat(content.pdfFile().url().toString()).isEqualTo("https://example.com/pdfFile.pdf");
    }

    @Test
    public void test_builders() {
        PdfFile urlPdfFile = PdfFile.builder()
            .url(URI.create("https://example.com/pdfFile.pdf"))
            .build();
        assertThat(new PdfFileContent(urlPdfFile))
            .isEqualTo(new PdfFileContent(urlPdfFile))
            .isEqualTo(PdfFileContent.from(urlPdfFile))
            .isEqualTo(PdfFileContent.from(urlPdfFile))
            .isEqualTo(new PdfFileContent(urlPdfFile.url()))
            .isEqualTo(new PdfFileContent(urlPdfFile.url().toString()))
            .isEqualTo(PdfFileContent.from(urlPdfFile.url()))
            .isEqualTo(PdfFileContent.from(urlPdfFile.url().toString()));

        PdfFile base64pdfFile = PdfFile.builder()
            .base64Data("cGRmDQo=")
            .build();
        assertThat(new PdfFileContent(base64pdfFile))
            .isEqualTo(new PdfFileContent(base64pdfFile))
            .isEqualTo(PdfFileContent.from(base64pdfFile));
    }
}
