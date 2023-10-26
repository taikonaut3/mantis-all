package io.github.astro.mantis.governance.router;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.github.astro.mantis.configuration.invoke.Invocation;

import java.util.Comparator;
import java.util.List;

import static io.github.astro.mantis.common.constant.ServiceType.Router.WEIGHT;

@ServiceProvider(WEIGHT)
public class WeightRouter implements Router {
    @Override
    public List<URL> route(List<URL> urls, Invocation invocation) {
        List<URL> weightUrls = urls.stream().sorted(Comparator.comparing(url -> url.getParameter(Key.WEIGHT))).toList();
        String maxWeight = weightUrls.get(weightUrls.size() - 1).getParameter(Key.WEIGHT);
        return urls.stream().filter(url -> convertCompare(url.getParameter(Key.WEIGHT), maxWeight)).toList();
    }

    private boolean convertCompare(String str1, String str2) {
        return Integer.parseInt(str1) == Integer.parseInt(str2);
    }
}
