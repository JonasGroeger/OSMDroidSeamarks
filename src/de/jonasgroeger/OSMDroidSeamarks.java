package de.jonasgroeger;

import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.TilesOverlay;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

/**
 * This activity displays the osm map with the seamarks overlay.
 * 
 * @author Jonas Groeger
 */
public class OSMDroidSeamarks extends Activity {

    // The controller to interact with to change something on the mapView
    private MapController              mapController;

    private MapView                    mapView;

    // A point where there are seamarks available
    private final GeoPoint             rostockPort = new GeoPoint(54.099, 12.145);

    // The tile source that provides the seamarks
    private final OnlineTileSourceBase seamarks    = new XYTileSource("seamarks", null, 0, 17, 256, ".png", "http://tiles.openseamap.org/seamark/");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        mapView = (MapView) findViewById(R.id.mapView);

        // For the map we use MAPNIK
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        // We want to show the zoom controls at the bottom of the screen
        mapView.setBuiltInZoomControls(true);

        // We want to ba able to double-tap zoom and stuff like that
        mapView.setMultiTouchControls(true);

        mapController = mapView.getController();

        // I think no explanation needed here
        mapController.setZoom(12);
        mapController.setCenter(rostockPort);

        // We create a tile provider that gives us access to the tiles. It gets
        // the tiles from the tile source seamarks
        final MapTileProviderBase tileProvider = new MapTileProviderBasic(this, seamarks);

        // We create a new overlay based on the provider
        final TilesOverlay seamarksOverlay = new TilesOverlay(tileProvider, this);

        // We want it to be transparent while its still loading
        seamarksOverlay.setLoadingBackgroundColor(Color.TRANSPARENT);

        // Add the overlay to the mapView
        mapView.getOverlays().add(seamarksOverlay);
    }
}
