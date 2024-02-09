package org.aksw.vaadin.yasqe.demo;

import org.aksw.vaadin.yasqe.RequestConfig;
import org.aksw.vaadin.yasqe.Yasqe;
import org.aksw.vaadin.yasqe.YasqeConfig;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(YasqeDemoSimple.NAV)
public class YasqeDemoSimple extends VerticalLayout
{
    private static final long serialVersionUID = 1L;
    public static final String NAV = "/simple";

    public YasqeDemoSimple() {
        setSizeFull();

        YasqeConfig config = new YasqeConfig();

        // The persistence id is used to store the content in the browser's local store
        config.setPersistenceId("yasqe-persistenceid-1");
        config.setPrefixCcApi("http://prefix.cc/");
        config.setShowShortLinkButton(true);

        // Other options:
        // config.setShowQueryButton(false);
        // config.setResizeable(false);

        // Create and add the MapContainer (which contains the map) to the UI
        final Yasqe yasqe = new Yasqe(config);
        yasqe.setSizeFull();
        this.add(yasqe);

        // The request config is only used to create share links and curl requests
        RequestConfig requestConfig = new RequestConfig()
                .setEndpoint("http://example.org/sparql")
                .setMethod("GET")
                .addHeader("Accept", "application/sparql-results+xml")
                .addDefaultGraph("http://default.graph")
                .addNamedGraph("http://named.graph");
        yasqe.setRequestConfig(requestConfig);

        // Setting the value will override local store content!
        // yasqe.addPrefixes(Map.of("foo", "urn:bar"));
        // yasqe.setValue("SELECT * { ?s ?p ?o } LIMIT 10");

        yasqe.addQueryButtonListener(ev -> {
            new Notification("Is abort? " + ev.isAbort() + " - " + ev.getValue(), 5000).open();
            ev.getSource().updateQueryButton(!ev.isAbort(), null);
        });

        // Here you could add your own URL shortener
        yasqe.setShortLinkHandler(ev -> {
            String str = ev.getValue();
            int l = str.length();
            // Return the first few characters
            return str.substring(0, Math.min(8, l));
        });
    }
}
