package com.danmor.parsers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

public interface ICollectionParser {

    default Collection<Material> parseMaterial(Collection<String> input) {
        Collection<Material> materialCollection = input.stream()
            .map(s -> {
            try {
                return Material.valueOf(s.toUpperCase());
            } catch (Exception e) {
                return null;
            }
        }).collect(Collectors.toList());

        materialCollection.remove(null);

        return materialCollection;
    }

    default Collection<Tag<Material>> parseTag(Collection<String> input) {
        Collection<Tag<Material>> materialCollection = input.stream()
            .map(s -> {
            try {
                NamespacedKey key = NamespacedKey.minecraft(s.toLowerCase());
                Tag<Material> tag = Bukkit.getTag(Tag.REGISTRY_BLOCKS, key, Material.class);
                return tag;
            } catch (Exception e) {
                return null;
            }
        }).collect(Collectors.toList());

        materialCollection.remove(null);

        return materialCollection;
    }
}
