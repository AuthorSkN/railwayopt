package com.railwayopt.mapview.graphic;

import java.awt.*;

/**
 * <p>Класс настройки стиля для точки на карте.</p>
 * <p>Позволяет настраивать: тип, цвет контура, цвет заливки и прозрачночть.</p>
 * @author Складнев Н.С.
 */
public class MapPointStyle {

    public static final int CIRCLE = 0;
    public static final int SQUARE = 1;
    public static final int TRIANGLE = 2;


    private String colorContour = "#000";
    private String colorFill = "#FFF";
    private int shape = CIRCLE;
    private int weightContour = 2;

    /**
     * <p>Констуктор</p>
     */
    public MapPointStyle(){}

    /**
     * <p>Конструктор</p>
     * @param shape фигура
     */
    public MapPointStyle(int shape){
        this.shape = shape;
    }

    /**
     * <p>Конструктор</p>
     * @param shape фигура
     * @param colorContour цвет контура
     * @param colorFill цвет заливки
     */
    public MapPointStyle(int shape, String colorContour, String colorFill){
        this(shape);
        this.colorContour = colorContour;
        this.colorFill = colorFill;
    }


    /**
     * <p>Возвращает цвет контура</p>
     * @return цвет контура в форме строки
     */
    public String getColorContour() {
        return colorContour;
    }

    /**
     * <p>Изменяет цвет конутра</p>
     * @param colorContour цвет конутра в форме строки
     */
    public void setColorContour(String colorContour) {
        this.colorContour = colorContour;
    }

    /**
     * <p>Возвращает цвет заливки</p>
     * @return цвет заливки в форме строки
     */
    public String getColorFill() {
        return colorFill;
    }

    /**
     * <p>Изменяет цвет заливки</p>
     * @param colorFill цвет заливки в форме строки
     */
    public void setColorFill(String colorFill) {
        this.colorFill = colorFill;
    }

    /**
     * <p>Возвращает форму фигуры</p>
     * @return форма
     */
    public int getShape() {
        return shape;
    }

    /**
     * <p>Изменяет форму фигуры</p>
     * @param shape форма фигуры
     */
    public void setShape(int shape) {
        this.shape = shape;
    }

    /**
     * <p>Возвращает вес конутра</p>
     * @return вес(относительная ширина) контура
     */
    public int getWeightContour() {
        return weightContour;
    }

    /**
     * <p>Изменяет вес конутра</p>
     * @param weightContour вес(относительная ширина) конутра
     */
    public void setWeightContour(int weightContour) {
        this.weightContour = weightContour;
    }


    /**
     * Герерирует диапазон различных цветов
     * @param count требуемое количество цветов
     * @return массив цветов
     */
    public static String[] generateDifferentColors(int count){
        String[] colors = new String[count];
        if(count <= 6){
            for(int i = 0, col = 0; i < count; i++, col+=60)  //реализация 6-ти основных диапазонов
                colors[i] = hsvToRgb(col, 100, 90);
        }else{
            int dCol = (int)(339.0 / count); //339 - верхняя граница фиолетового
            for(int i = 0, col = 0; i < count; i++, col+=dCol)  //простое деление
                colors[i] = hsvToRgb(col, 100, 90);
        }
        return colors;
    }

    /**
     * Генерирует массив оттенков заданного базового цвета
     * @param baseColor базовый цвет
     * @param count требуемое количество оттенков
     * @return массив оттенков
     */
    public static String[] generateHues(String baseColor, int count){
        String[] colors = new String[count];
        int r = Integer.parseInt(baseColor.substring(1,3), 16);
        int g = Integer.parseInt(baseColor.substring(3,5), 16);
        int b = Integer.parseInt(baseColor.substring(5,7), 16);
        float[] hsv = new float[3];
        Color.RGBtoHSB(r, g,b, hsv);
        int baseHue = (int)(hsv[0] * 360.0);
        int baseSet = (int)(hsv[1]*100);
        int lVal = 100; //светлые
        int mVal = 75; //средние
        int dVal = 50; //тёмные
        //Генерация происходит по двум пространствам: hue - деление, val - 3 значения
        int dColHue = 40 / (count + 1); //40 - диапазон разброса тона
        int beginHue = ((baseHue - 20) < 0)? baseHue - 20 + 360 : baseHue - 20; //левая граница оттенков цвета
        for(int i = 0, hue = beginHue+dColHue; i < count; i += 3, hue += dColHue) //обход светлых
            colors[i] = hsvToRgb(hue, baseSet, lVal);

        for(int i = 1, hue = beginHue+dColHue; i < count; i += 3, hue += dColHue) //обход средних
            colors[i] = hsvToRgb(hue, baseSet, mVal);

        for(int i = 2, hue = beginHue+dColHue; i < count; i += 3, hue += dColHue) //обход тёмных
            colors[i] = hsvToRgb(hue, baseSet, dVal);
        return colors;
    }



    //Переводит из hsv в rgb
    private static String hsvToRgb(int hue, int sat, int val) {
        double r = 0,g = 0,b = 0;
        int i = (int)(hue / 60.0);
        double Vmin = (val * (100 - sat)) / 100;
        double a = (val - Vmin)*(hue % 60)/60;
        double Vdec = val-a;
        double Vinc = Vmin+a;
        switch (i) {
            case 0: r = val; g = Vinc; b = Vmin; break;
            case 1: r = Vdec; g = val; b = Vmin; break;
            case 2: r = Vmin; g = val; b = Vinc; break;
            case 3: r = Vmin; g = Vdec; b = val; break;
            case 4: r = Vinc; g = Vmin; b = val; break;
            case 5: r = val; g = Vmin; b = Vdec; break;
        }
        return rgbToString(r*255/100, g*255/100, b*255/100);
    }
    //Формирует строковый вид rgb
    private static String rgbToString(double r, double g, double b) {
        String rs = (r == 0)? "00" : Integer.toHexString((int)r);
        String gs = (g == 0)? "00" :Integer.toHexString((int)g);
        String bs = (b == 0)? "00" :Integer.toHexString((int)b);
        return "#"+rs + gs + bs;
    }
}
