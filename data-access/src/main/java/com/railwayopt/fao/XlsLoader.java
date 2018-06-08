package com.railwayopt.fao;

import com.railwayopt.RegionManager;
import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Station;
import com.sun.media.sound.InvalidFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Класс загрузчика сущностей из эксель-документов
 */
public class XlsLoader {
    /**
     * Допутимое расширение для загрузчика
     */
    public static String FILE_EXTENSION_2 = ".xls";

    /**
     * Документ с данными
     */
    private Workbook book;
    /**
     * Таблица с данными в документе
     */
    private Sheet dataSheet;
    /**
     * Указатель на текущую обрабатваемую строку таблицы
     */
    private Row currentRow;
    /**
     * Указатель на текущую обрабатваемую клетку таблицы
     */
    private Cell currentCell;
    /**
     * Номер столбца весов в документе
     */
    private int weightColumnNum = -1;
    /**
     * Номер столбца айди в документе
     */
    private int idColumnNum = -1;
    /**
     * Номер столбца долгот в документе
     */
    private int longColumnNum = -1;
    /**
     * Номер столбца широт в документе
     */
    private int lattColumnNum = -1;
    /**
     * Номер столбца имен в документе
     */
    private int nameColumnNum = -1;

    private int regionColumnNum = -1;
    /**
     * Номер столбца показателей КП в документе
     */
    private int logisticCenterColumnNum = -1;

    private int classColumnNum = -1;
    private int fullWeightColumnNum = -1;
    private int cargoesColumnName = -1;
    private int typeColumnName = -1;
    private RegionManager regionManager = new RegionManager();

    /**
     * Конструктор для создания загрузчика данных из электронных таблиц
     *
     * @param xlsFile
     * @throws IOException
     * @throws InvalidFormatException
     */
    public XlsLoader(File xlsFile) throws IOException, InvalidFormatException  {
        /*if(xlsFile.getName().endsWith(FILE_EXTENSION_1)){
            book = new XSSFWorkbook(xlsFile);
        } else*/
        if (xlsFile .getName().endsWith(FILE_EXTENSION_2)) {
            book = new HSSFWorkbook(new FileInputStream(xlsFile));
        } else
            throw new IllegalArgumentException("Файл имел некорректное расширение. Допустимые расширения: " /*+ FILE_EXTENSION_1 + ", "*/ + FILE_EXTENSION_2);
        dataSheet = book.getSheetAt(0);
    }

    private void readHeader() {
        //Чтение шапок:
        currentRow = dataSheet.getRow(0);
        currentCell = currentRow.getCell(0);
        //int nameCol = -1;
        int i = 0; //текущий номер ячейки в строке заголовков
        while (currentCell != null) {
            String cellString = currentCell.getStringCellValue().toLowerCase().trim();
            String token = cellString;
            switch (token) {
                case "longitude":
                case "long":
                case "lng":
                    longColumnNum = i;
                    break;
                case "latitude":
                case "lat":
                case "lt":
                    lattColumnNum = i;
                    break;
                case "weight":
                    weightColumnNum = i;
                    break;
                case "fullweight":
                    fullWeightColumnNum = i;
                    break;
                case "id":
                    idColumnNum = i;
                    break;
                case "name":
                    nameColumnNum = i;
                    break;
                case "logisticcenter":
                case "lc":
                    logisticCenterColumnNum = i;
                    break;
                case "region": regionColumnNum = i; break;
                case "class": classColumnNum = i; break;
                case "cargoes": cargoesColumnName = i; break;
                case "type": typeColumnName = i; break;
                case "": break;
                default:
                    throw new IllegalArgumentException("Unknown token: \"" + token + "\" from string \"" + cellString + "\" from cell " + currentCell.toString());
            }
            currentCell = currentRow.getCell(++i);
        }
    }

    private Factory readProd() {
        //id:
        currentCell = currentRow.getCell(idColumnNum);
        int id = (int) currentCell.getNumericCellValue();
        //weight:
        currentCell = currentRow.getCell(weightColumnNum);
        if (currentCell == null) {
            int stop = 0;
        }
        double weight = currentCell.getNumericCellValue();
        //longitude:
        double longitude = -1;
        if (longColumnNum != -1) {
            currentCell = currentRow.getCell(longColumnNum);
            longitude = currentCell.getNumericCellValue();
        }
        //lattitude:
        double latitude = -1;
        if (lattColumnNum != -1) {
            currentCell = currentRow.getCell(lattColumnNum);
            latitude = currentCell.getNumericCellValue();
        }
        //name:
        String name = "";
        if (nameColumnNum != -1) {
            name = currentRow.getCell(nameColumnNum).getStringCellValue();
        }
        //region
        String region = "";
        if (regionColumnNum != -1) {
            region = currentRow.getCell(regionColumnNum).getStringCellValue();
        }
        //result:
        Factory factory = new Factory(id, name, longitude, latitude, weight);
        factory.setRegion(regionManager.getCorrectRegion(region));
        return factory;
    }

    private Station readStation() {
        //id:
        currentCell = currentRow.getCell(idColumnNum);
        int id = (int) currentCell.getNumericCellValue();
        //weight:
        double weight = 1;
        if (weightColumnNum >= 0) {
            currentCell = currentRow.getCell(weightColumnNum);
            weight = currentCell.getNumericCellValue();
        }
        //longitude:
        currentCell = currentRow.getCell(longColumnNum);
        double longitude = currentCell.getNumericCellValue();
        //lattitude:
        currentCell = currentRow.getCell(lattColumnNum);
        double lattitude = currentCell.getNumericCellValue();
        //name:
        String name = "";
        if (nameColumnNum != -1) {
            name = currentRow.getCell(nameColumnNum).getStringCellValue();
        }
        //islogiticCenteR:
        boolean isLC = false;
        if (logisticCenterColumnNum != -1) {
            double cellValue = currentRow.getCell(logisticCenterColumnNum).getNumericCellValue();
            isLC = (cellValue == 1) ? true : false;
        }
        Integer stationClass = null;
        if (classColumnNum != -1) {
            stationClass = (int)currentRow.getCell(classColumnNum).getNumericCellValue();
        }

        //region
        String region = "";
        if (regionColumnNum != -1) {
            region = currentRow.getCell(regionColumnNum).getStringCellValue();
        }
        //result:s
        Station stat = new Station(id, name, longitude, lattitude, isLC);
        stat.setStationClass(stationClass);
        stat.setRegion(regionManager.getCorrectRegion(region));
        return stat;
    }

    /**
     * Метод получения списка всех производств из файла
     *
     * @return
     */
    public List<Factory> readFactories() {
        List<Factory> productions = new LinkedList<>();
        readHeader();
        int i = 1;
        currentRow = dataSheet.getRow(i);
        currentCell = currentRow.getCell(0);
        while ((currentRow != null) && (currentCell.getCellType() != Cell.CELL_TYPE_BLANK)) {
            Factory nextProd = readProd();
            productions.add(nextProd);
            currentRow = dataSheet.getRow(++i);
            if (currentRow != null) {
                currentCell = currentRow.getCell(0);
            }
        }
        return productions;
    }

    /**
     * Метод получения списка всех станций из файла
     *
     * @return
     */
    public List<Station> readStations() {
        List<Station> stations = new ArrayList<>();
        readHeader();
        int i = 1;
        currentRow = dataSheet.getRow(i);
        currentCell = currentRow.getCell(0);
        while ((currentCell != null) && (currentCell.getCellType() != CellType.BLANK.getCode())) {
            Station nextStat = readStation();
            stations.add(nextStat);
            currentRow = dataSheet.getRow(++i);
            currentCell = (currentRow == null) ? null : currentRow.getCell(0);
        }
        return stations;
    }

    /*///report stuff:

    public void readReport(List<Production> allProds, List<Station> allStations, MapView mapView) {
        mapView.clearPoints();
        //knrc-kp:
        Sheet secondLevelSheet = book.getSheetAt(1);
        int rowIndex = 0;
        currentRow = secondLevelSheet.getRow(rowIndex);
        currentCell = currentRow.getCell(1);
        //Инициализация основных цветов:
        int knrcCount = (int) currentCell.getNumericCellValue();
        String[] generalColors = MapPointStyle.generateDifferentColors(knrcCount);
        rowIndex = 7;
        currentRow = secondLevelSheet.getRow(rowIndex++);
        currentCell = currentRow.getCell(0);
        int colorIter = 0;
        ArrayList<MapPoint> allKpMp = new ArrayList<>();  //Представления КП
        while ((currentRow != null) && (currentCell != null) && (currentCell.getCellType() != CellType.BLANK.getCode())) {
            //Добавление кнрц:
            int id = (int) currentCell.getNumericCellValue();   //id кнрц
            int mapId = IDmanager.STATION_OFFSET + id;
            Station knrc = (Station) findStationById(id, allStations);   //Кнрц
            Point knrcPoint = new Point(knrc.getLatitude(), knrc.getLongitude());  //Кнрц
            MapPoint knrcMP = new MapPoint(mapId, knrcPoint, knrc.getName(), new MapPointStyle(MapPointStyle.TRIANGLE, "#999999", generalColors[colorIter]), 10);
            mapView.addPoint(knrcMP, true);
            //добавление соответствующих кп:
            currentCell = currentRow.getCell(3);
            String kpIdsStr = currentCell.getStringCellValue();
            if (!kpIdsStr.equals("")) {
                Station[] kps = (Station[]) findStationsById(kpIdsStr, allStations);
                Point[] kpsPoints = new Point[kps.length];
                MapPoint[] kpsMPs = new MapPoint[kps.length];
                String[] kpColors = MapPointStyle.generateHues(generalColors[colorIter++], kps.length);
                int kpColorIter = 0;
                for (int i = 0; i < kps.length; i++) {
                    kpsPoints[i] = new Point(kps[i].getLatitude(), kps[i].getLongitude());
                    kpsMPs[i] = new MapPoint(kps[i].getId() + IDmanager.STATION_OFFSET, kpsPoints[i], kps[i].getName(), new MapPointStyle(MapPointStyle.SQUARE, "aaaaaa", kpColors[kpColorIter++]), 6);
                    try {
                        mapView.addPoint(kpsMPs[i], true);
                    } catch (AlreadyExistsMapObjException exc) {
                        continue;
                    }
                }
                allKpMp.addAll(Arrays.asList(kpsMPs));
            }
            currentRow = secondLevelSheet.getRow(rowIndex++);
            currentCell = (currentRow == null) ? null : currentRow.getCell(0);
        }
        //kp-prods:
        Sheet firstLevelSheet = book.getSheetAt(0);
        rowIndex = 7;
        currentRow = firstLevelSheet.getRow(rowIndex++);
        currentCell = currentRow.getCell(0);
        while ((currentRow != null) && (currentCell != null) && (currentCell.getCellType() != CellType.BLANK.getCode())) {
            int id = (int) currentCell.getNumericCellValue();
            int mapId = id + IDmanager.STATION_OFFSET;
            MapPoint kpMapPoint = findMapPointById(mapId, allKpMp);
            MapPointStyle prodStyle = new MapPointStyle(MapPointStyle.CIRCLE, "#999999", kpMapPoint.getStyle().getColorFill());
            currentCell = currentRow.getCell(3);
            String prodsIdsStr = currentCell.getStringCellValue();
            if (!prodsIdsStr.equals("")) {
                Production[] prods = findProductionsByIds(prodsIdsStr, allProds);
                Point[] prodsPoints = new Point[prods.length];
                MapPoint[] prodsMapPoints = new MapPoint[prods.length];
                for (int i = 0; i < prods.length; i++) {
                    Production prod = prods[i];
                    prodsPoints[i] = new Point(prods[i].getLatitude(), prods[i].getLongitude());
                    prodsMapPoints[i] = new MapPoint(prod.getId() + IDmanager.PRODUCTION_OFFSET, prodsPoints[i], prod.getName(), prodStyle, 3);
                    mapView.addPoint(prodsMapPoints[i],true);
                }
            }
            currentRow = firstLevelSheet.getRow(rowIndex++);
            currentCell = (currentRow == null) ? null : currentRow.getCell(0);
        }
    }

    private Station findStationById(int id, List<Station> stats) {
        for (Station sta : stats) {
            if (sta.getId() == id) return sta;
        }
        return null;
    }

    private MapPoint findMapPointById(int id, Collection<MapPoint> mpoints) {
        for (MapPoint mp : mpoints) {
            if (mp.getID() == id) return mp;
        }
        return null;
    }

    private Production findProductionById(int id, List<Production> prods) {
        for (Production prod : prods) {
            if (prod.getId() == id) return prod;
        }
        return null;
    }

    private Station[] findStationsById(String ids, List<Station> infrs) {
        ArrayList<Station> res = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(ids, ",");
        String idStr;
        while (tokenizer.hasMoreElements()) {
            idStr = tokenizer.nextToken().trim();
            int id = Integer.valueOf(idStr);
            res.add(findStationById(id, infrs));
        }
        return res.toArray(new Station[0]);
    }

    private Production[] findProductionsByIds(String ids, List<Production> prods) {
        ArrayList<Infrastructure> res = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(ids, ",");
        String idStr;
        while (tokenizer.hasMoreElements()) {
            idStr = tokenizer.nextToken().trim();
            int id = Integer.valueOf(idStr);
            res.add(findProductionById(id, prods));
        }
        return res.toArray(new Production[0]);
    }*/
}