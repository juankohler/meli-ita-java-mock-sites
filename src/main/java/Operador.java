public class Operador <E>{
    public static <E extends Comparable<E>> E min(E[] list) {
        E minValue = list[0];
        for(int i = 1; i < list.length; i++) {
            if(minValue.compareTo(list[i])>0) {
                minValue = list[i];
                return minValue;
            }
        }
        return minValue;
    }
    public static <E extends Comparable<E>> E max(E[] list) {
        E maxValue = list[0];
        for(int i = 1; i < list.length; i++) {
            if(maxValue.compareTo(list[i])<0) {
                maxValue = list[i];
                return maxValue;
            }
        }
        return maxValue;
    }

    public static <E extends Comparable<E>> void ordenar (E[] arr) {
        E aux = null;
        for (int i = 0; i< arr.length ; ++i){
            for (int j = 0; j < arr.length - i - 1; j++){
                if (arr[j+1].compareTo((arr[j])) < 0 ) {

                    aux = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = aux;
                }
            }
        }
    }

    public static <E extends Comparable<E>> int find(E[] list, E element) {

        for(int i = 0; i < list.length; i++) {
            if(list[i].compareTo(element) == 0) {
                return i;
            }
        }
        return -1;
    }

}
