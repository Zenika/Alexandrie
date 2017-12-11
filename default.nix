with import <nixpkgs> {};
stdenv.mkDerivation {
    name = "alexandrie-dev";
    buildInputs = [
            pkgs.openjdk9
            pkgs.nodejs
            pkgs.gradle
    ];
}
