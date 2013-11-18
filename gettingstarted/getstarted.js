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
