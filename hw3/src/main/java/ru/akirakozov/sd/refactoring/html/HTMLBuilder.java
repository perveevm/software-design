package ru.akirakozov.sd.refactoring.html;

import java.util.ArrayList;
import java.util.List;

public class HTMLBuilder {
    private String header = "";
    private List<String> body = new ArrayList<>();

    public void setHeader(final String header, final int headerLevel) {
        if (headerLevel == 0) {
            this.header = header;
        } else {
            this.header = String.format("<h%d>%s</h%d>", headerLevel, header, headerLevel);
        }
    }

    public void addBodyLine(final String line) {
        body.add(line);
    }

    public String getHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>\n");
        if (!header.isEmpty()) {
            sb.append(header).append("\n");
        }
        sb.append(String.join("<br/>\n", body));
        sb.append("</body></html>\n");
        return sb.toString();
    }

    public void clear() {
        header = "";
        body.clear();
    }

    public String getHtmlAndClear() {
        String result = getHtml();
        clear();
        return result;
    }
}
