package com.smartstocks.product.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NamedNativeQuery(name="Stock.topGainers", query = "WITH clean AS (\n" +
        "\tSELECT * FROM stock_sub ss WHERE (ss.\"close\" != 0 OR ss.high != 0 OR ss.low != 0 OR ss.\"open\" != 0) AND SUBSTRING(ss.symbol, LENGTH(ss.symbol)-1, LENGTH(ss.symbol)) = 'NS'\n" +
        "),\n" +
        "\n" +
        "cleanup AS (\n" +
        "SELECT MIN(ss.stock_id) AS stock_id, MIN(ss.\"close\") AS close_stock, MIN(ss.high) AS high_stock, MIN(ss.low) AS low_stock, MIN(ss.\"open\") AS open_stock, MIN(ss.volume) AS volume_stock, ss.symbol, ss.\"date\" \n" +
        "FROm clean ss \n" +
        "WHERE ss.\"date\" < EXTRACT(epoch FROM now()) AND ss.\"date\" > (EXTRACT(epoch FROM now()) - ( :days * 86400)) \n" +
        "GROUP BY ss.\"date\", ss.symbol \n" +
        "),\n" +
        "\n" +
        "mini AS (\n" +
        "SELECT symbol, MIN(close_stock) as close_stock FROM cleanup WHERE \"date\" IN (SELECT MIN(c.\"date\") FROM cleanup c GROUP BY symbol) GROUP BY symbol\n" +
        "), \n" +
        "\n" +
        "maxi AS (\n" +
        "SELECT symbol, MAX(open_stock) as open_stock FROM cleanup WHERE \"date\" IN (SELECT MAX(c.\"date\") FROM cleanup c GROUP BY symbol) GROUP BY symbol\n" +
        ")\n" +
        "\n" +
        "SELECT \n" +
        "\t((maxi.open_stock - mini.close_stock)/GREATEST(mini.close_stock, 1))*100 AS overall_change_perc,\n" +
        "\tmaxi.open_stock - mini.close_stock as overall_change,\n" +
        "\tmini.symbol \n" +
        "FROM maxi JOIN mini ON maxi.symbol = mini.symbol \n" +
        "ORDER BY overall_change_perc DESC " +
        "LIMIT 10", resultSetMapping = "mapping")
@NamedNativeQuery(name="Stock.topLosers", query = "WITH clean AS (\n" +
        "\tSELECT * FROM stock_sub ss WHERE (ss.\"close\" != 0 OR ss.high != 0 OR ss.low != 0 OR ss.\"open\" != 0) AND SUBSTRING(ss.symbol, LENGTH(ss.symbol)-1, LENGTH(ss.symbol)) = 'NS'\n" +
        "),\n" +
        "\n" +
        "cleanup AS (\n" +
        "SELECT MIN(ss.stock_id) AS stock_id, MIN(ss.\"close\") AS close_stock, MIN(ss.high) AS high_stock, MIN(ss.low) AS low_stock, MIN(ss.\"open\") AS open_stock, MIN(ss.volume) AS volume_stock, ss.symbol, ss.\"date\" \n" +
        "FROm clean ss \n" +
        "WHERE ss.\"date\" < EXTRACT(epoch FROM now()) AND ss.\"date\" > (EXTRACT(epoch FROM now()) - ( :days * 86400)) \n" +
        "GROUP BY ss.\"date\", ss.symbol \n" +
        "),\n" +
        "\n" +
        "mini AS (\n" +
        "SELECT symbol, MIN(close_stock) as close_stock FROM cleanup WHERE \"date\" IN (SELECT MIN(c.\"date\") FROM cleanup c GROUP BY symbol) GROUP BY symbol\n" +
        "), \n" +
        "\n" +
        "maxi AS (\n" +
        "SELECT symbol, MAX(open_stock) as open_stock FROM cleanup WHERE \"date\" IN (SELECT MAX(c.\"date\") FROM cleanup c GROUP BY symbol) GROUP BY symbol\n" +
        ")\n" +
        "\n" +
        "SELECT \n" +
        "\t((maxi.open_stock - mini.close_stock)/GREATEST(mini.close_stock, 1))*100 AS overall_change_perc,\n" +
        "\tmaxi.open_stock - mini.close_stock as overall_change,\n" +
        "\tmini.symbol \n" +
        "FROM maxi JOIN mini ON maxi.symbol = mini.symbol \n" +
        "ORDER BY overall_change_perc ASC " +
        "LIMIT 10", resultSetMapping = "mapping")
@SqlResultSetMapping(name="mapping", classes = {
        @ConstructorResult(
                targetClass = com.smartstocks.product.dto.TopStocks.class,
                columns = {
                        @ColumnResult(name="overall_change_perc", type=Double.class),
                        @ColumnResult(name="overall_change", type=Double.class),
                        @ColumnResult(name="symbol", type = String.class)
                })})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "stock_sub")
public class Stock {

    @Id
    private long stockId;

    private int date;

    private String symbol;

    private float open;

    private float high;

    private float low;

    private float close;

    private float volume;

}
