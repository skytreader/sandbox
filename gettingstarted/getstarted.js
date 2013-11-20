/**
Getting started tutorial for three.js.

See: http://www.aerotwist.com/tutorials/getting-started-with-three-js/
*/
var WIDTH = 400;
var HEIGHT = 300;

// Set camera attributes
var VIEW_ANGLE = 45;
var ASPECT = WIDTH / HEIGHT;
var NEAR = 0.1;
var FAR = 10000;

var container = $("#container");

// WebGL objects
var renderer = new THREE.WebGLRenderer();
var camera = new THREE.PerspectiveCamera(VIEW_ANGLE, ASPECT, NEAR, FAR);

var scene = new THREE.Scene();

scene.add(camera);

// The camera is intially positioned at (0, 0, 0). Pull back.
camera.position.z = 300;

// Start the renderer.
renderer.setSize(WIDTH, HEIGHT);

container.append(renderer.domElement);

/**Mesh material*/
var sphereMaterial = new THREE.MeshLambertMaterial({color:0xcc0000});

/**Creating the mesh*/

var radius = 50;
var segments = 16;
var rings = 16;

/*
Create a new mesh with sperical geometry.
*/
var geometryObj = new THREE.SphereGeometry(radius, segments, rings);
var sphere = new THREE.Mesh(geometryObj, sphereMaterial);

var pointLight = new THREE.PointLight(0xffffff);

pointLight.position.x = 10;
pointLight.position.y = 50;
pointLight.position.z = 130;

scene.add(pointLight);

renderer.render(scene, camera);
