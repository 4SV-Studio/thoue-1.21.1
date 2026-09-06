#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

void main() {
    vec4 tex = texture(Sampler0, texCoord0);

    // Discard near-invisible texels entirely: they write nothing to color
    // OR depth, so water/particles show through cleanly there (this is
    // what fixes "solid dark background" from the very first request).
    if (tex.a < 0.08) {
        discard;
    }

    // For everything else, push alpha up toward opaque so this pixel wins
    // the depth test against water/glass drawn later - that's what makes
    // the orb read as being IN FRONT rather than tinted/behind. Water
    // always draws after entities in vanilla's pipeline, so only a
    // depth-write "win" (not draw order) can put the orb visually on top.
    float a = pow(tex.a, 0.35);

    vec4 color = vec4(tex.rgb, a) * vertexColor * ColorModulator;
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}