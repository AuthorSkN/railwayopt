package com.railwayopt.dataimport;



/**
 *
 * @author alexUnder
 */
public class XlsSaver{

    public enum ReportType{
        FIRST_LEVEL,
        SECOND_LEVEL
    }

     XlsSaver(){}

   /* //запись шапок:
    private static void writeHeaderMapObj(Sheet sheet){
        Row rowHeader = sheet.createRow(0);
        //запись:
        int i=0;
        //название:
        Cell cellName = rowHeader.createCell(i++);
        cellName.setCellValue("name");
        //долгота:
        Cell cellLong = rowHeader.createCell(i++);
        cellLong.setCellValue("longitude");
        //широта:
        Cell cellLat = rowHeader.createCell(i++);
        cellLat.setCellValue("latitude");
        //x:
        Cell cellX = rowHeader.createCell(i++);
        cellX.setCellValue("x");
        //y:
        Cell cellY = rowHeader.createCell(i++);
        cellY.setCellValue("y");
        //вес:
        Cell cellWeight = rowHeader.createCell(i++);
        cellWeight.setCellValue("weight");
        //ID:
        Cell cellId = rowHeader.createCell(i);
        cellId.setCellValue("id");
    }
    private static  void writeHeaderProd(Sheet sheet){
        writeHeaderMapObj(sheet);
    }
    private static  void writeHeaderStat(Sheet sheet){
        writeHeaderMapObj(sheet);
        Row rowHeader = sheet.getRow(0);
        //запись:
        int i=7;
        //isLogisticCenter:
        Cell cellLc = rowHeader.createCell(i);
        cellLc.setCellValue("lc");
    }

    //запись одного экземпляра:
    private static  void writeMapObj(Row rowToWrite, Infrastructure mo){
        int i =0;
        //запись:
        //название:
        Cell cellName = rowToWrite.createCell(i++);
        cellName.setCellValue(mo.getName());
        //долгота:
        Cell cellLong = rowToWrite.createCell(i++);
        cellLong.setCellValue(mo.getLongitude());
        //широта:
        Cell cellLat = rowToWrite.createCell(i++);
        cellLat.setCellValue(mo.getLatitude());
        //x:
        Cell cellX = rowToWrite.createCell(i++);
        cellX.setCellValue(mo.getX());
        //y:
        Cell cellY = rowToWrite.createCell(i++);
        cellY.setCellValue(mo.getY());
        //вес:
        Cell cellWeight = rowToWrite.createCell(i++);
        cellWeight.setCellValue(mo.getWeight());
        //ID:
        Cell cellId = rowToWrite.createCell(i);
        cellId.setCellValue(mo.getId());
    }
    private static  void writeProd(Row rowToWrite, Production prod){
        writeMapObj(rowToWrite,prod);
        //остальные поля:
        //(их пока нет)
    }
    private static  void writeStat(Row rowToWrite, Station stat){
        writeMapObj(rowToWrite,stat);
        //остальные поля:
        int i=7;
        //isLogisticCenter:
        int cellValue = (stat.isLogisticCenter())? 1 : 0;
        Cell cellIsLc = rowToWrite.createCell(i);
        cellIsLc.setCellValue(cellValue);
    }

    //запись коллекций:
    public static  void saveProductions(List<Production> prods, File fileToSave) throws IOException, InvalidFormatException {
        Workbook wb = new XSSFWorkbook(fileToSave);
        Sheet datasheet = wb.createSheet("Производства");
        writeHeaderProd(datasheet);
        int i=1;
        Row currentRow = datasheet.createRow(i++);
        //запись:
        for(Production prod : prods){
            writeProd(currentRow, prod);
            currentRow = datasheet.createRow(i++);
        }
        ////
        FileOutputStream fos = new FileOutputStream(fileToSave);
        try{
            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            fos.flush();
            fos.close();
        }
    }
    public static  void saveStations(List<Station> stats, File fileToSave) throws IOException, InvalidFormatException {
        Workbook wb = new XSSFWorkbook(fileToSave);
        Sheet datasheet = wb.createSheet("Станции");
        writeHeaderProd(datasheet);
        int i=1;
        Row currentRow = datasheet.createRow(i++);
        //запись:
        for(Station stat : stats){
            writeStat(currentRow, stat);
            currentRow = datasheet.createRow(i++);
        }
        ////
        FileOutputStream fos = new FileOutputStream(fileToSave);
        try{
            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            fos.flush();
            fos.close();
        }
    }
    //формирование отчёта:
    private static  void fillSheet(Project proj, ReportType reportType, Sheet destinationSheet) {
        List<Cluster> clusters = proj.getClusters();
        //общая сводка,расчёт:
        int clustersCount = clusters.size();
        int moCount=0;
        double sumweight = 0;
        double totalDist = 0;
        double complexity = 0;
        for(Cluster cluster : clusters){
            for(Infrastructure mo : cluster){
                moCount++;
                double distance = Infrastructure.distance(mo,cluster);
                double weight = mo.getWeight();
                sumweight+=weight;
                totalDist+=distance;
                complexity+=distance*weight;
            }
        }
        double averToCentrs = totalDist/moCount;
        double averBetweenCentrs = Util.averDistanceBetweenClusters(clusters);
        //общая сводка,формирование документа:
        int iterCell = 0;
        int iterRow = 0;
        //число кластеров:
        Row row = destinationSheet.createRow(iterRow++);
        Cell cell = row.createCell(iterCell++);
        cell.setCellValue("Количество кластеров:");
        cell = row.createCell(iterCell++);
        cell.setCellValue(clustersCount);
        //сумма весов:
        row = destinationSheet.createRow(iterRow++);
        iterCell = 0;
        cell = row.createCell(iterCell++);
        cell.setCellValue("Общий объём перерабатываемой продукции(тыс.т):");
        cell = row.createCell(iterCell++);
        cell.setCellValue(sumweight);
        //сред. расстояние между кластерами:
        row = destinationSheet.createRow(iterRow++);
        iterCell = 0;
        cell = row.createCell(iterCell++);
        cell.setCellValue("Среднее расстояние между кластерами(км):");
        cell = row.createCell(iterCell++);
        cell.setCellValue(averBetweenCentrs);
        //среднее расстояние от предприятий до центров:
        row = destinationSheet.createRow(iterRow++);
        iterCell = 0;
        cell = row.createCell(iterCell++);
        String averToCenterTotal = (reportType==ReportType.FIRST_LEVEL)?
                "Среднее расстояние от предприятий до центров кластеров(км):" :
                "Среднее расстояние от станций до центров кластеров(км):";
        cell.setCellValue(averToCenterTotal);
        cell = row.createCell(iterCell++);
        cell.setCellValue(averToCentrs);
        //суммарное расстояние:
        row = destinationSheet.createRow(iterRow++);
        iterCell = 0;
        cell = row.createCell(iterCell++);
        String sumToCenterTotal = (reportType==ReportType.FIRST_LEVEL)?
                "Суммарное расстояние от предприятий до центров кластеров:" :
                "Суммарное расстояние от станций до центров кластеров:";
        cell.setCellValue(sumToCenterTotal);
        cell = row.createCell(iterCell++);
        cell.setCellValue(totalDist);
        //сложность перевозки:
        row = destinationSheet.createRow(iterRow++);
        iterCell = 0;
        cell = row.createCell(iterCell++);
        cell.setCellValue("Суммарный объём перевозок(тыс.т * км):");
        cell = row.createCell(iterCell++);
        cell.setCellValue(complexity);
        //таблица,формирование документа,шапки:
        row = destinationSheet.createRow(iterRow++);
        iterCell = 0;
        //id:
        cell = row.createCell(iterCell++);
        cell.setCellValue("ID");
        //именование:
        cell = row.createCell(iterCell++);
        cell.setCellValue("Именование");
        //кол-во предприятий:
        cell = row.createCell(iterCell++);
        String N = (reportType==ReportType.FIRST_LEVEL)?
                "Кол-во предприятий, входящих в кластер" :
                "Кол-во станций, входящих в кластер";
        cell.setCellValue(N);
        //ID предприятий,входящих в кластер
        cell = row.createCell(iterCell++);
        String idsThis = (reportType==ReportType.FIRST_LEVEL)?
                "ID предприятий, входящих в кластер" :
                "ID станций, входящих в кластер";
        cell.setCellValue(idsThis);
        //Объём переработки кластера(тыс.т)
        cell = row.createCell(iterCell++);
        cell.setCellValue("Объем переработки кластера(тыс.т)");
        //% от общего объёма
        cell = row.createCell(iterCell++);
        cell.setCellValue("% от общего объёма");
        //Среднее расстояние от предприятия до центра кластера (км)
        cell = row.createCell(iterCell++);
        String averToCenterThis = (reportType==ReportType.FIRST_LEVEL)?
                "Среднее расстояние от предприятий до центра кластера (км)" :
                "Среднее расстояние от станций до центра кластера (км)";
        cell.setCellValue(averToCenterThis);
        //Суммарное расстояние от предприятий до центра кластера (км)
        cell = row.createCell(iterCell++);
        String sumToCenterThis = (reportType==ReportType.FIRST_LEVEL)?
                "Суммарное расстояние от предприятий до центра кластера (км)" :
                "Суммарное расстояние от станций до центра кластера (км)";
        cell.setCellValue(sumToCenterThis);
        //таблица,формирование документа,тело:
        row = destinationSheet.createRow(iterRow++);
        iterCell = 0;
        for (Cluster cluster : clusters) {
            //info about cluster,calculating:
            String ids = cluster.getIds();
            double sumOfWeights = cluster.getSumOfWeights();
            double percent = (sumOfWeights/sumweight)*100;
            double averToCenter = cluster.getAverDistanceToCenter();
            double sumToCenter = cluster.getSumDistanceToCenter();
            //ID:
            cell = row.createCell(iterCell++);
            cell.setCellValue(cluster.getStation().getId());
            //name:
            cell = row.createCell(iterCell++);
            cell.setCellValue(cluster.getStation().getName());
            //кол-во предприятий:
            cell = row.createCell(iterCell++);
            cell.setCellValue(cluster.getElements().size());
            //ID предприятий:
            cell = row.createCell(iterCell++);
            cell.setCellValue(ids);
            //объём переработки:
            cell = row.createCell(iterCell++);
            cell.setCellValue(sumOfWeights);
            //% от общего объёма
            cell = row.createCell(iterCell++);
            cell.setCellValue(percent+"%");
            //Среднее расстояние от предприятия до центра кластера
            cell = row.createCell(iterCell++);
            cell.setCellValue(averToCenter);
            //Суммарное расстояние от предприятий до центра кластера (км)
            cell = row.createCell(iterCell++);
            cell.setCellValue(sumToCenter);
            ///next iteration settings:
            row = destinationSheet.createRow(iterRow++);
            iterCell = 0;
        }
    }
    public static void saveReport(DoubleClustering doubleClustering, File fileToSave) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet1 = wb.createSheet("1st level");
        Sheet sheet2 = wb.createSheet("2nd level");
        fillSheet(doubleClustering.getFirstLevel(),ReportType.FIRST_LEVEL,sheet1);
        fillSheet(doubleClustering.getSecondLevel(),ReportType.SECOND_LEVEL,sheet2);
        FileOutputStream fos=null;
        try{
            fos = new FileOutputStream(fileToSave);
            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fos!=null) {
                fos.flush();
                fos.close();
            }
        }
    }*/
}