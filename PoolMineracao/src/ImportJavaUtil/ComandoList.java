package ImportJavaUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComandoList {
    public String ComandoLista() {
        List<String> lista = new ArrayList<>();
        lista.add("Maçã");
        lista.add("Banana");
        lista.add("Laranja");

        // Ordenar a lista
        Collections.sort(lista);

        // Imprimir a lista ordenada
        for (String item : lista) {
            System.out.println(item);
        }

        // Usando um Map
        Map<String, Integer> mapa = new HashMap<>();
        mapa.put("Um", 1);
        mapa.put("Dois", 2);
        mapa.put("Três", 3);

        // Iterar sobre as entradas do mapa
        for (Map.Entry<String, Integer> entrada : mapa.entrySet()) {
            System.out.println(entrada.getKey() + " => " + entrada.getValue());
        }
		return "";
    }
}